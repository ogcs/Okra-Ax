package test;

import org.ogcs.ax.component.bean.AxCoInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 伪造数据
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/5
 * @since 1.0
 */
public interface AxDatas {

    // Module ID
    long MODULE_ID_GATE_0 = 100L;
    long MODULE_ID_GATE_1 = 101L;

    long MODULE_ID_LOGIN_0 = 200L;

    long MODULE_ID_CHAT_0 = 500L;
    long MODULE_ID_CHAT_1 = 501L;
    long MODULE_ID_CHAT_2 = 502L;

    long MODULE_ID_CHESS_0 = 600L;
    long MODULE_ID_CHESS_1 = 601L;
    long MODULE_ID_CHESS_2 = 602L;


    // Ax Component Map
    // 对外端口：10000
    // login: 7000+
    // gate: 8000+
    // remote: 9000+

    Map<String, List<AxCoInfo>> map = new HashMap<String, List<AxCoInfo>>() {{
        put(String.valueOf(1), new ArrayList<AxCoInfo>() {{
            add(new AxCoInfo(MODULE_ID_CHAT_0, "192.168.2.29", 9000));
            add(new AxCoInfo(MODULE_ID_CHAT_1, "192.168.2.29", 9001));
        }});
        put(String.valueOf(2), new ArrayList<AxCoInfo>() {{
            add(new AxCoInfo(MODULE_ID_CHESS_0, "192.168.2.29", 9000));
            add(new AxCoInfo(MODULE_ID_CHESS_1, "192.168.2.29", 9001));
            add(new AxCoInfo(MODULE_ID_CHESS_2, "192.168.2.29", 9002));
        }});
        put(String.valueOf(3), new ArrayList<AxCoInfo>() {{
            add(new AxCoInfo(MODULE_ID_GATE_0, "192.168.2.29", 8000, 10000));
            add(new AxCoInfo(MODULE_ID_GATE_1, "192.168.2.29", 8001, 10001));
        }});
        put(String.valueOf(4), new ArrayList<AxCoInfo>() {{
            add(new AxCoInfo(MODULE_ID_LOGIN_0, "192.168.2.29", 7000, 11000));
        }});
    }};
}
