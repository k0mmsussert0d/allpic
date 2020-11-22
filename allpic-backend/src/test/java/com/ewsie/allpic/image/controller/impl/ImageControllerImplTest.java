package com.ewsie.allpic.image.controller.impl;

import com.amazonaws.SdkClientException;
import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImageDTOWithContent;
import com.ewsie.allpic.image.model.UploadImageDetails;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.LoadImageService;
import com.ewsie.allpic.image.service.SaveImageService;
import com.ewsie.allpic.image.service.UnpublishImageService;
import com.ewsie.allpic.user.controller.impl.SpringSecurityForUserControllerImplTestConfig;
import com.ewsie.allpic.user.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = SpringSecurityForUserControllerImplTestConfig.class
)
@AutoConfigureMockMvc
class ImageControllerImplTest {

    public static final String TOKEN = "ABCDEF";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserDTO testUser;

    @MockBean
    LoadImageService loadImageService;

    @MockBean
    SaveImageService saveImageService;

    @MockBean
    ImageDTOService imageDTOService;

    @MockBean
    UnpublishImageService unpublishImageService;

    @Test
    public void whenGetImage_andImageDoesNotExist_shouldReturnNotFoundError() throws Exception {

        Mockito.when(loadImageService.loadImage(TOKEN)).thenThrow(NullPointerException.class);

        mockMvc.perform(get("/img/i/" + TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetImage_andAmazonSdkThrowsAnError_shouldReturnServiceUnavailable() throws Exception {

        Mockito.when(loadImageService.loadImage(TOKEN)).thenThrow(SdkClientException.class);

        mockMvc.perform(get("/img/i/" + TOKEN))
                .andExpect(status().isServiceUnavailable());
    }

    @ParameterizedTest
    @MethodSource("getSampleFilesAndTheirMimeTypes")
    public void whenGetImage_andImageExists_shouldReturnFileBinaryDataWithProperContentTypeHeader(
            String testFilePath,
            MediaType testFileMimeType
    ) throws Exception {

        ImageDTO imageDTO = ImageDTO.builder()
                .token(TOKEN)
                .uploadTime(LocalDateTime.now())
                .isPublic(true)
                .isActive(true)
                .build();

        InputStream testImageISForMock = this.getClass().getResourceAsStream(testFilePath);
        InputStream testImageISForAssert = this.getClass().getResourceAsStream(testFilePath);

        ImageDTOWithContent imageDTOWithContent = ImageDTOWithContent.builder()
                .imageDTO(imageDTO)
                .content(testImageISForMock)
                .contentLength(testImageISForMock.available())
                .contentType(testFileMimeType.toString())
                .build();

        Mockito.when(loadImageService.loadImage(TOKEN)).thenReturn(imageDTOWithContent);

        mockMvc.perform(get("/img/i/" + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(testFileMimeType))
                .andExpect(content().bytes(testImageISForAssert.readAllBytes()));
    }

    @Test
    public void whenGetImageThumbnail_andImageDoesNotExist_shouldReturnNotFoundError() throws Exception {

        Mockito.when(loadImageService.loadThumb(TOKEN)).thenThrow(NullPointerException.class);

        mockMvc.perform(get("/img/i/thumb/" + TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetImageThumbnail_andAmazonSdkThrowsAnError_shouldReturnServiceUnavailable() throws Exception {

        Mockito.when(loadImageService.loadThumb(TOKEN)).thenThrow(SdkClientException.class);

        mockMvc.perform(get("/img/i/thumb/" + TOKEN))
                .andExpect(status().isServiceUnavailable());
    }

    @ParameterizedTest
    @MethodSource("getSampleFilesAndTheirMimeTypes")
    public void whenGetImageThumbnail_andImageExists_shouldReturnFileBinaryDataWithProperContentTypeHeader(
            String testFilePath,
            MediaType testFileMimeType
    ) throws Exception {

        ImageDTO imageDTO = ImageDTO.builder()
                .token(TOKEN)
                .uploadTime(LocalDateTime.now())
                .isPublic(true)
                .isActive(true)
                .build();

        InputStream testImageISForMock = this.getClass().getResourceAsStream(testFilePath);
        InputStream testImageISForAssert = this.getClass().getResourceAsStream(testFilePath);

        ImageDTOWithContent imageDTOWithContent = ImageDTOWithContent.builder()
                .imageDTO(imageDTO)
                .content(testImageISForMock)
                .contentLength(testImageISForMock.available())
                .contentType(testFileMimeType.toString())
                .build();

        Mockito.when(loadImageService.loadThumb(TOKEN)).thenReturn(imageDTOWithContent);

        mockMvc.perform(get("/img/i/thumb/" + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(testFileMimeType))
                .andExpect(content().bytes(testImageISForAssert.readAllBytes()));
    }

    @Test
    public void whenUploadImage_asAnonymousUser_shouldReturnSavedImageDetails() throws Exception {

        InputStream testImageInputStream = this.getClass().getResourceAsStream("/test.jpg");

        MockMultipartFile imageFilePart = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG.toString(),
                testImageInputStream
        );

        UploadImageDetails imageDetails = new UploadImageDetails();
        imageDetails.setTitle("sample title");
        imageDetails.setPublic(true);
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile imageDetailsPart = new MockMultipartFile(
                "metadata",
                "",
                MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsBytes(imageDetails)
        );

        Mockito.when(
                saveImageService.saveImage(
                        Mockito.any(InputStream.class),
                        Mockito.eq("test.jpg"),
                        Mockito.eq(imageDetails.getTitle()),
                        Mockito.eq(imageDetails.isPublic()),
                        Mockito.isNull()))
                .thenReturn(
                        ImageDTO.builder()
                                .id(1L)
                                .title(imageDetails.getTitle())
                                .isPublic(imageDetails.isPublic())
                                .isActive(true)
                                .token(TOKEN)
                                .uploadTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0))
                        .build());

        mockMvc.perform(multipart("/img/upload")
                    .file(imageFilePart)
                    .file(imageDetailsPart))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "  \"id\": 1," +
                        "  \"isActive\": true," +
                        "  \"isPublic\": true," +
                        "  \"title\": \"sample title\"," +
                        "  \"token\": \"ABCDEF\"," +
                        "  \"uploadTime\": \"2020-01-01T12:00:00\"," +
                        "  \"uploader\": null" +
                        "}"));
    }

    @Test
    @WithUserDetails("username")
    public void whenUploadImage_asRegisteredUser_shouldReturnSavedImageDetails() throws Exception {

        InputStream testImageInputStream = this.getClass().getResourceAsStream("/test.jpg");

        MockMultipartFile imageFilePart = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG.toString(),
                testImageInputStream
        );

        UploadImageDetails imageDetails = new UploadImageDetails();
        imageDetails.setTitle("sample title");
        imageDetails.setPublic(true);
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile imageDetailsPart = new MockMultipartFile(
                "metadata",
                "",
                MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsBytes(imageDetails)
        );

        Mockito.when(
                saveImageService.saveImage(
                        Mockito.any(InputStream.class),
                        Mockito.eq("test.jpg"),
                        Mockito.eq(imageDetails.getTitle()),
                        Mockito.eq(imageDetails.isPublic()),
                        Mockito.any(UserDTO.class)))
                .thenReturn(
                        ImageDTO.builder()
                                .id(1L)
                                .title(imageDetails.getTitle())
                                .isPublic(imageDetails.isPublic())
                                .isActive(true)
                                .token(TOKEN)
                                .uploadTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0))
                                .uploader(testUser)
                                .build());

        mockMvc.perform(multipart("/img/upload")
                .file(imageFilePart)
                .file(imageDetailsPart))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "  \"id\": 1," +
                        "  \"isActive\": true," +
                        "  \"isPublic\": true," +
                        "  \"title\": \"sample title\"," +
                        "  \"token\": \"ABCDEF\"," +
                        "  \"uploadTime\": \"2020-01-01T12:00:00\"," +
                        "  \"uploader\": \"username\"" +
                        "}"));
    }

    @Test
    public void whenUploadImage_withNoExtension_shouldReturnBadRequestError() throws Exception {
        InputStream testImageInputStream = this.getClass().getResourceAsStream("/test.jpg");

        MockMultipartFile imageFilePart = new MockMultipartFile(
                "file",
                "test",
                MediaType.IMAGE_JPEG.toString(),
                testImageInputStream
        );

        UploadImageDetails imageDetails = new UploadImageDetails();
        imageDetails.setTitle("sample title");
        imageDetails.setPublic(true);
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile imageDetailsPart = new MockMultipartFile(
                "metadata",
                "",
                MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsBytes(imageDetails)
        );

        Mockito.when(
                saveImageService.saveImage(
                        Mockito.any(InputStream.class),
                        Mockito.eq("test"),
                        Mockito.eq(imageDetails.getTitle()),
                        Mockito.eq(imageDetails.isPublic()),
                        Mockito.isNull()))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(multipart("/img/upload")
                .file(imageFilePart)
                .file(imageDetailsPart))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUploadImage_andAmazonSdkThrowsAnError_shouldReturnServiceUnavailable() throws Exception {
        InputStream testImageInputStream = this.getClass().getResourceAsStream("/test.jpg");

        MockMultipartFile imageFilePart = new MockMultipartFile(
                "file",
                "test.jpg",
                MediaType.IMAGE_JPEG.toString(),
                testImageInputStream
        );

        UploadImageDetails imageDetails = new UploadImageDetails();
        imageDetails.setTitle("sample title");
        imageDetails.setPublic(true);
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile imageDetailsPart = new MockMultipartFile(
                "metadata",
                "",
                MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsBytes(imageDetails)
        );

        Mockito.when(
                saveImageService.saveImage(
                        Mockito.any(InputStream.class),
                        Mockito.eq("test.jpg"),
                        Mockito.eq(imageDetails.getTitle()),
                        Mockito.eq(imageDetails.isPublic()),
                        Mockito.isNull()))
                .thenThrow(SdkClientException.class);

        mockMvc.perform(multipart("/img/upload")
                .file(imageFilePart)
                .file(imageDetailsPart))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void whenGetMostRecentImages_andNoImagesWereUploaded_thenReturnEmptyList() throws Exception {

        Mockito.when(imageDTOService.findAllOrderByUploadedTimeDesc())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/img/recent"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void whenGetMostRecentImages_andThereAreImagesUploaded_thenReturnListOfImages() throws Exception {

        ImageDTO imageDTOWithTitle = getSampleImage(4L, "token4");
        imageDTOWithTitle.setTitle("title");

        Mockito.when(imageDTOService.findAllOrderByUploadedTimeDesc())
                .thenReturn(List.of(
                        getSampleImage(1L, "token1"),
                        getSampleImage(2L, "token2"),
                        getSampleImage(3L, "token3"),
                        imageDTOWithTitle
                ));

        mockMvc.perform(get("/img/recent"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[\n" +
                                "  {\n" +
                                "    \"token\": \"token1\",\n" +
                                "    \"title\": null\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"token\": \"token2\",\n" +
                                "    \"title\": null\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"token\": \"token3\",\n" +
                                "    \"title\": null\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"token\": \"token4\",\n" +
                                "    \"title\": \"title\"\n" +
                                "  }\n" +
                                "]"
                ));
    }

    @Test
    public void whenRemoveImage_andUserIsAnonymous_thenReturnUnauthorizedError() throws Exception {

        mockMvc.perform(delete("/img/" + TOKEN))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"MOD"})
    public void whenRemoveImage_andUserHasRoleMod_thenReturnRemovedImage() throws Exception {

        ImageDTO imageDTO = getSampleImage(1L, TOKEN);

        Mockito.when(unpublishImageService.hideImageByToken(imageDTO.getToken()))
                .thenReturn(imageDTO);

        mockMvc.perform(delete("/img/" + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "  \"id\": 1," +
                        "  \"isActive\": true," +
                        "  \"isPublic\": true," +
                        "  \"title\": null," +
                        "  \"token\": \"ABCDEF\"," +
                        "  \"uploadTime\": \"2020-01-01T12:00:00\"," +
                        "  \"uploader\": null" +
                        "}"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenRemoveImage_andUserHasRoleAdmin_thenReturnRemovedImage() throws Exception {

        ImageDTO imageDTO = getSampleImage(1L, TOKEN);

        Mockito.when(unpublishImageService.hideImageByToken(imageDTO.getToken()))
                .thenReturn(imageDTO);

        mockMvc.perform(delete("/img/" + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "  \"id\": 1," +
                        "  \"isActive\": true," +
                        "  \"isPublic\": true," +
                        "  \"title\": null," +
                        "  \"token\": \"ABCDEF\"," +
                        "  \"uploadTime\": \"2020-01-01T12:00:00\"," +
                        "  \"uploader\": null" +
                        "}"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenRemoveImage_andImageDoesNotExist_thenReturnNotFoundError() throws Exception {

        Mockito.when(unpublishImageService.hideImageByToken(TOKEN))
                .thenThrow(NullPointerException.class);

        mockMvc.perform(delete("/img/" + TOKEN))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> getSampleFilesAndTheirMimeTypes() {
        return Stream.of(
                Arguments.of("/test.jpg", MediaType.IMAGE_JPEG),
                Arguments.of("/test.png", MediaType.IMAGE_PNG),
                Arguments.of("/test.gif", MediaType.IMAGE_GIF)
        );
    }

    private ImageDTO getSampleImage(Long id, String token) {
        return ImageDTO.builder()
                .id(id)
                .token(token)
                .isActive(true)
                .isPublic(true)
                .uploadTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0, 0))
                .build();
    }
}
