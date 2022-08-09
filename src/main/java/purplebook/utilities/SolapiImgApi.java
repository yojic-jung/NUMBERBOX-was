package purplebook.utilities;

import purplebook.model.request.ImageModel;
import purplebook.model.response.DeleteImageResult;
import purplebook.model.response.GetImageListModel;
import purplebook.model.response.ImageInfoResult;
import purplebook.model.response.ImageResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

// 문서 : https://docs.solapi.com/api-reference/storage
public interface SolapiImgApi {
    // 이미지 등록
    @POST("storage/v1/files")
    Call<ImageResult> createImage(@Header("Authorization") String auth,
                                  @Body ImageModel image);

    // 이미지 정보 가져오기
    @GET("storage/v1/files/{fileId}")
    Call<ImageInfoResult> getImageInfo(@Header("Authorization") String auth,
                                       @Path("fileId") String fileId);

    // 이미지 리스트 가져오기
    @GET("storage/v1/files")
    Call<GetImageListModel> getImageList(@Header("Authorization") String auth);

    // 이미지 삭제
    @DELETE("storage/v1/files/{fileId}")
    Call<DeleteImageResult> deleteImageInfo(@Header("Authorization") String auth,
                                       @Path("fileId") String fileId);
}
