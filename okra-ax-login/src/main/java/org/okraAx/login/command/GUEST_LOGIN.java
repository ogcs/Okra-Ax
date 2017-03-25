//package org.okraAx.login.command;
//
//import com.alibaba.fastjson.JSON;
//import org.okraAx.login.HttpUtil;
//import org.ogcs.app.AppContext;
//import org.ogcs.app.Command;
//import org.ogcs.app.Session;
//import org.okraAx.internal.bean.AxCoInfo;
//import org.okraAx.internal.config.ServerProperties;
//import org.okraAx.internal.SpringContext;
//import org.okraAx.internal.inner.AxInnerClient;
//import org.okraAx.utilities.AxReplys;
//import org.okraAx.room.manager.AxInnerCoManager;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Guest login. without password or any other auth information.
// */
//public class GUEST_LOGIN implements Command<Session, Map<String, String>> {
//
//    private AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
//
//    @Override
//    public void execute(Session session, Map<String, String> params) throws Exception {
//        // 登录 返回授权和
//        String id = params.get("id");
//
//        AxInnerClient client = components.getByHash("gate", id); // Modules.GATE
//        if (client == null) {
//            HttpUtil.response(session, "{\"state\": -1 }");
//            return;
//        }
//
//        AxCoInfo info = client.getInfo();
//
//        long uid = ServerProperties.id();   //  生成一个新的id
//        long auth = System.currentTimeMillis() + (long) (Math.random() * 86400000);   //  随机一个数字作为授权码 - TODO: 有效期一天 - 0点移除
//        client.session().writeAndFlush(
//                AxReplys.axInbound(-1, -1,
//                        1001, // LOGIN_AUTH
//                        GpbLogin.ReqLoginAuth.newBuilder()
//                                .setUid(uid)
//                                .setAuth(auth)
//                                .build()
//                )
//        );
//        Map<String, Object> result = new HashMap<>();
//        result.put("state", 0);
//        result.put("uid", uid);
//        result.put("auth", auth);
//        result.put("host", info.getHost());
//        result.put("port", info.getBind());
//        HttpUtil.response(session, JSON.toJSONString(result));
//    }
//}
