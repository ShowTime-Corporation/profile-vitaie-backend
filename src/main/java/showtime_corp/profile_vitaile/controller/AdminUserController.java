package showtime_corp.profile_vitaile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import showtime_corp.profile_vitaile.dto.AdminUserRequestDTO;
import showtime_corp.profile_vitaile.dto.UserResponseDTO;
import showtime_corp.profile_vitaile.service.AdminUserService;

import java.util.List;

/**
 * REST controller for administrative user management.
 * <p>
 * Provides endpoints for creating, listing, updating, retrieving, and deleting
 * users.
 * Access is restricted to users with the 'ADMIN' role.
 * </p>
 *
 * @author Santiago Toro y Andres Niebles
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin Users", description = "Endpoints for managing users (Admin only)")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class AdminUserController {

        private final AdminUserService adminUserService;

        /**
         * Lists all users in the system.
         *
         * @return A list of all registered users.
         */
        @Operation(summary = "List all users", description = "Retrieves a list of all users. Requires ADMIN role.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden - Requires ADMIN role", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
                return ResponseEntity.ok(adminUserService.getAllUsers());
        }

        /**
         * Retrieves a specific user by their ID.
         *
         * @param id The unique identifier of the user.
         * @return The user details.
         */
        @Operation(summary = "Get user by ID", description = "Retrieves detailed information of a specific user. Requires ADMIN role.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden - Requires ADMIN role", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<UserResponseDTO> getUserById(
                        @Parameter(description = "ID of the user to retrieve", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(adminUserService.getUserById(id));
        }

        /**
         * Updates an existing user.
         *
         * @param id      The ID of the user to update.
         * @param request The updated user data.
         * @return The updated user details.
         */
        @Operation(summary = "Update user", description = "Updates an existing user's information. Requires ADMIN role.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Email conflict", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden - Requires ADMIN role", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<UserResponseDTO> updateUser(
                        @Parameter(description = "ID of the user to update", required = true) @PathVariable Long id,
                        @Valid @RequestBody AdminUserRequestDTO request) {
                return ResponseEntity.ok(adminUserService.updateUser(id, request));
        }

        /**
         * Deletes a user by their ID.
         *
         * @param id The ID of the user to delete.
         * @return No content status.
         */
        @Operation(summary = "Delete user", description = "Deletes a user from the system. Requires ADMIN role.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "User deleted successfully", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "Forbidden - Requires ADMIN role", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(
                        @Parameter(description = "ID of the user to delete", required = true) @PathVariable Long id) {
                adminUserService.deleteUser(id);
                return ResponseEntity.noContent().build();
        }
}