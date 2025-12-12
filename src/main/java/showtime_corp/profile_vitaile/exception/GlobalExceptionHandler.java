package showtime_corp.profile_vitaile.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

/**
 * Global exception handler for the application.
 * <p>
 * This class intercepts all exceptions thrown by controllers and converts them into
 * standardized {@link ProblemDetail} responses following the RFC 7807 specification.
 * <p>
 * Each exception is logged, enriched with a timestamp and a correlation/trace ID,
 * and then transformed into a consistent error payload.
 * <p>
 * The main goals are:
 * <ul>
 *     <li>Provide unified error responses</li>
 *     <li>Add correlation IDs for debugging</li>
 *     <li>Improve API observability and traceability</li>
 *     <li>Prevent exposing internal stack traces to clients</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
@Tag(name = "Global Exception Handler", description = "Handles all application-level exceptions by returning standardized ProblemDetail responses.")
public class GlobalExceptionHandler {

    private static final String BASE_URI = "https://coopvitae.com/errors/";
    private static final String TRACE_ID_KEY = "traceId";

    /**
     * Retrieves the correlation ID stored in the MDC.
     * If the interceptor did not set one, a fallback UUID is generated.
     *
     * @return A valid correlation ID string.
     */
    private String getCorrelationId() {
        String traceId = MDC.get(TRACE_ID_KEY);
        return traceId != null ? traceId : "N/A-" + UUID.randomUUID();
    }

    /**
     * Builds a standardized ProblemDetail object for any handled exception.
     *
     * @param status   HTTP status code to return.
     * @param title    Short title describing the error.
     * @param detail   Detailed explanation of the error.
     * @param type     A unique error type identifier to append to BASE_URI.
     * @param traceId  Correlation ID to track the request.
     * @return A fully structured ProblemDetail instance.
     */
    private ProblemDetail buildProblem(
            HttpStatus status,
            String title,
            String detail,
            String type,
            String traceId
    ) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        pd.setType(URI.create(BASE_URI + type));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty(TRACE_ID_KEY, traceId);
        return pd;
    }

    // ============================================================
    // 403 FORBIDDEN
    // ============================================================

    /**
     * Handles ForbiddenException by returning a 403 Forbidden ProblemDetail.
     */
    @Operation(summary = "Handles ForbiddenException", description = "Triggered when the client attempts to access a protected resource without the required permissions.")
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(ForbiddenException.class)
    public ProblemDetail handleForbiddenException(ForbiddenException e) {
        String traceId = getCorrelationId();
        log.error("ForbiddenException: {}", e.getMessage(), e);
        return buildProblem(HttpStatus.FORBIDDEN, e.getMessage(), e.getMessage(), "ForbiddenException", traceId);
    }

    // ============================================================
    // 401 UNAUTHORIZED
    // ============================================================

    /**
     * Handles UnauthorizedException by returning a 401 Unauthorized ProblemDetail.
     */
    @Operation(summary = "Handles UnauthorizedException", description = "Occurs when authentication is required but missing or invalid.")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail handleUnauthorizedException(UnauthorizedException e) {
        String traceId = getCorrelationId();
        log.warn("UnauthorizedException: {}", e.getMessage(), e);
        return buildProblem(HttpStatus.UNAUTHORIZED, e.getMessage(), e.getMessage(), "UnauthorizedException", traceId);
    }

    // ============================================================
    // 404 NOT FOUND
    // ============================================================

    /**
     * Handles ResourceNotFoundException by returning a 404 Not Found ProblemDetail.
     */
    @Operation(summary = "Handles ResourceNotFoundException", description = "Returned when the requested resource does not exist.")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e) {
        String traceId = getCorrelationId();
        log.warn("Resource not found (404): {}", e.getMessage(), e);
        return buildProblem(HttpStatus.NOT_FOUND, e.getMessage(), e.getMessage(), "ResourceNotFoundException", traceId);
    }

    // ============================================================
    // 409 CONFLICT
    // ============================================================

    /**
     * Handles ConflictException by returning a 409 Conflict ProblemDetail.
     */
    @Operation(summary = "Handles ConflictException", description = "Occurs when a resource already exists or a database conflict is detected.")
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflictException(ConflictException e) {
        String traceId = getCorrelationId();
        log.warn("Conflict (409): {}", e.getMessage(), e);
        return buildProblem(HttpStatus.CONFLICT, e.getMessage(), e.getMessage(), "ConflictException", traceId);
    }

    // ============================================================
    // 422 UNPROCESSABLE ENTITY
    // ============================================================

    /**
     * Handles UnprocessableEntityException (422).
     */
    @Operation(summary = "Handles UnprocessableEntityException", description = "Triggered when input is syntactically correct but semantically invalid.")
    @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(UnprocessableEntityException.class)
    public ProblemDetail handleUnprocessableEntityException(UnprocessableEntityException e) {
        String traceId = getCorrelationId();
        log.warn("Unprocessable entity (422): {}", e.getMessage(), e);
        return buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e.getMessage(), "UnprocessableEntityException", traceId);
    }

    // ============================================================
    // 500 INTERNAL SERVER ERROR
    // ============================================================

    /**
     * Handles InternalServerErrorException (500).
     */
    @Operation(summary = "Handles InternalServerErrorException", description = "Represents unexpected server-side errors.")
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(InternalServerErrorException.class)
    public ProblemDetail handleInternalServerErrorException(InternalServerErrorException e) {
        String traceId = getCorrelationId();
        log.error("Internal server error (500): {}", e.getMessage(), e);
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getMessage(), "InternalServerErrorException", traceId);
    }

    // ============================================================
    // 400 BAD REQUEST (Custom)
    // ============================================================

    /**
     * Handles BadRequestException (custom).
     */
    @Operation(summary = "Handles BadRequestException", description = "Thrown when the client sends invalid or malformed data.")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException e) {
        String traceId = getCorrelationId();
        log.warn("Bad Request (400): {}", e.getMessage(), e);
        return buildProblem(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage(), "BadRequestException", traceId);
    }

    // ============================================================
    // 400 BAD REQUEST (Validation errors)
    // ============================================================

    /**
     * Handles validation errors thrown by @Valid on DTOs.
     */
    @Operation(summary = "Handles validation errors", description = "Triggered when DTO fields annotated with @Valid fail validation rules.")
    @ApiResponse(responseCode = "400", description = "Validation Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        String traceId = getCorrelationId();

        String message = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return buildProblem(
                HttpStatus.BAD_REQUEST,
                message,
                "Validation failed",
                "ValidationException",
                traceId
        );
    }

    // ============================================================
    // 408 REQUEST TIMEOUT
    // ============================================================

    /**
     * Handles RequestTimeoutException (408).
     */
    @Operation(summary = "Handles RequestTimeoutException", description = "Returned when a request exceeds the maximum processing time.")
    @ApiResponse(responseCode = "408", description = "Request Timeout", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ExceptionHandler(RequestTimeoutException.class)
    public ProblemDetail handleRequestTimeoutException(RequestTimeoutException e) {
        String traceId = getCorrelationId();
        log.warn("Request Timeout (408): {}", e.getMessage(), e);
        return buildProblem(HttpStatus.REQUEST_TIMEOUT, e.getMessage(), e.getMessage(), "RequestTimeoutException", traceId);
    }
}
