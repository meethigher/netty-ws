package top.meethigher.nettywsserver.config;

/**
 * WebSocket事件
 *
 * @author chenchuancheng
 * @since 2023/1/13 10:17
 */
public interface WsEvent {

    /**
     * 定义二个事件，分别为两个不同时间点开会内容。早会、晚会
     */

    //早会
    String MORNING_MEETING = "morning-meeting";
    //晚会
    String EVENING_MEETING = "evening-meeting";
    //通用消息
    String MESSAGE = "message";
}
