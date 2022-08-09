package purplebook.solapi.app;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import purplebook.model.response.GroupModel;
import purplebook.utilities.APIInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 한번 요청으로 1만건까지 알림톡 발송이 가능합니다.
 */
public class SendJsonAlimtalkButton {
	
	public static final  Logger logger = LoggerFactory.getLogger(SendJsonAlimtalkButton.class);
	
    public static void sendAlimTalkButtonCertifySuccess(String name, String phone) {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();

        // 웹링크 버튼
        JsonObject btn1 = new JsonObject();
        btn1.addProperty("buttonType", "WL");
        btn1.addProperty("buttonName", "수업 목록 보기");
        btn1.addProperty("linkMo", "https://m.coksabu.com/tutorpage");
        btn1.addProperty("linkPc", "https://coksabu.com/tutorpage");


        JsonArray  buttons = new JsonArray();
        buttons.add(btn1);

        JsonObject kakaoOptions1 = new JsonObject();
        kakaoOptions1.addProperty("pfId", "KA01PF210329072824713TdpP5oO4q1K");
        kakaoOptions1.addProperty("templateId", "KA01TP210415155839174LUVsqi8oXhB");
        kakaoOptions1.addProperty("title","인증 완료");
        kakaoOptions1.add("buttons", buttons);

        JsonObject msg = new JsonObject();
        msg.addProperty("to", phone);
        msg.addProperty("from", "01086491176");
        msg.addProperty("text", name+" 선생님(미소)\n" + 
        		"선생님 본인인증 및 학력인증 완료 되었습니다.(축하)\n" + 
        		"정상적으로 수업 노출이 가능하며,\n" + 
        		"앱을 다운 받으시면\n" + 
        		"실시간 과외알림을 받을 수 있습니다.\n" + 
        		"\n" + 
        		"※아직 수업을 안 만드셨다면,\n" + 
        		"수업을 만들어 선생님의 수업을 적극 (별)홍보해보세요(별)");
        
        
        msg.add("kakaoOptions", kakaoOptions1);
        messages.add(msg);


        // ... 최대 1만건까지 추가 가능

        params.add("messages", messages);

        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), params);
        api.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                // 성공 시 200이 출력됩니다.
                if (response.isSuccessful()) {
                    GroupModel body = response.body();
                    logger.info("statusCode : " + response.code()+"groupId : " + body.getGroupId()+"status: " + body.getStatus()+"count: " + body.getCount().toString());
                } else {
                    try {
                    	logger.warn(response.errorBody().string());
                    } catch (IOException e) {
                        logger.warn(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    
    
    public static void sendAlimTalkButtonCertifyFail(String name, String phone) {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();

        // 웹링크 버튼
        JsonObject btn1 = new JsonObject();
        btn1.addProperty("buttonType", "WL");
        btn1.addProperty("buttonName", "인증 다시하기");
        btn1.addProperty("linkMo", "https://m.coksabu.com/tutorpage");
        btn1.addProperty("linkPc", "https://coksabu.com/tutorpage");


        JsonArray  buttons = new JsonArray();
        buttons.add(btn1);

        JsonObject kakaoOptions1 = new JsonObject();
        kakaoOptions1.addProperty("pfId", "KA01PF210329072824713TdpP5oO4q1K");
        kakaoOptions1.addProperty("templateId", "KA01TP210401070535373XwWKLb4fMBB");
        kakaoOptions1.addProperty("title","선생님 인증반려");
        kakaoOptions1.add("buttons", buttons);

        JsonObject msg = new JsonObject();
        msg.addProperty("to", phone);
        msg.addProperty("from", "01086491176");
        msg.addProperty("text", name+" 선생님 인증반려,\n" + 
        		"선생님의 본인 인증 및 학력인증이 반려되었습니다.\n" + 
        		"인증 이미지를 확인 후 다시 제출해주시기 바랍니다.\n" + 
        		"※대학원을 등록한 경우 대학원 인증 이미지까지 등록하여 주시기 바랍니다.");
        
        
        msg.add("kakaoOptions", kakaoOptions1);
        messages.add(msg);


        // ... 최대 1만건까지 추가 가능

        params.add("messages", messages);

        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), params);
        api.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                // 성공 시 200이 출력됩니다.
                if (response.isSuccessful()) {
                    GroupModel body = response.body();
                    logger.info("statusCode : " + response.code()+"groupId : " + body.getGroupId()+"status: " + body.getStatus()+"count: " + body.getCount().toString());
                } else {
                    try {
                    	logger.warn(response.errorBody().string());
                    } catch (IOException e) {
                    	logger.warn(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    
    
}
