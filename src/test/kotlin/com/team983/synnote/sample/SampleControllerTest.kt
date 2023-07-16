package com.team983.synnote.sample

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.web.context.WebApplicationContext
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest
class SampleControllerTest {
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(context: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun getSampleByIdTest() {
        val sampleId = "aaa"
        mockMvc.perform(
            get("/api/v1/samples/{sampleId}", sampleId)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("sampleId", `is`(sampleId)))
            .andExpect(jsonPath("name", `is`("sample-$sampleId")))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "sample",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Sample")
                        .description("Get a sample by id")
                        .pathParameters(
                            parameterWithName("sampleId").description("the sample id")
                        )
                        .responseFields(
                            fieldWithPath("sampleId").type(JsonFieldType.STRING).description("The sample identifier."),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("The name of sample.")
                        )
                )
            )
    }
}
