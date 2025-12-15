package showtime_corp.profile_vitaile.service.serviceai;

import org.springframework.stereotype.Component;

@Component
public class AiPromptFactory {

    public String employabilityPrompt(String cvText) {
        return """
        You are a senior technical recruiter and employability analyst.

        ABSOLUTE RULES:
        - Analyze ONLY the provided CV.
        - Do NOT repeat information between sections.
        - No introductions.
        - No conclusions.
        - No explanations.
        - Output MUST follow the EXACT format below.

        OUTPUT FORMAT (MANDATORY):

        ===TECH_OFFER===
        [Technical roles, seniority, required skills, missing skills]

        ===EDUCATION_OFFER===
        [Certifications, courses, academic paths based on gaps]

        ===COMPANY_OFFER===
        [Company types, industries, team profiles]

        CV CONTENT:
        """ + cvText;
    }

    public String roadmapPrompt(String cvText) {
        return """
        You are a senior career roadmap architect.

        ABSOLUTE RULES:
        - 12 to 24 month roadmap.
        - Concrete steps only.
        - No motivational language.
        - No repetition.
        - Output MUST follow the EXACT format below.

        OUTPUT FORMAT (MANDATORY):

        ===ROAD_ANALYSIS===
        [Current professional situation]

        ===ROAD_PROPOSAL===
        [Target roles and career direction]

        ===ROAD_IDEAS===
        [Skills, projects, certifications, actions]

        ===ROAD_KEEP===
        [Strengths and habits to maintain]

        CV CONTENT:
        """ + cvText;
    }

    public String resumePrompt(String cvText) {
        return """
        You are a senior technical recruiter and resume evaluator.

        ABSOLUTE RULES:
        - Analyze ONLY the provided CV.
        - Each section MUST be UNIQUE.
        - No repetition.
        - Recruiter-oriented language.
        - No introductions.
        - No conclusions.
        - Output MUST follow the EXACT format below.

        OUTPUT FORMAT (MANDATORY):

        ===RESUME_INFO===
        [Deep professional analysis]

        ===RESUME_EMPLOYABILITY===
        [Market fit, seniority, hiring probability]

        ===RESUME_SIMPLE===
        [Ultra-short recruiter view, max 6 bullet points]

        ===RESUME_RECOMMENDATION===
        [Concrete improvements]

        CV CONTENT:
        """ + cvText;
    }
}
