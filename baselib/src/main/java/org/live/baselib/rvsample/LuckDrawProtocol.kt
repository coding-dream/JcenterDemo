package org.live.baselib.rvsample

/**
 * Created by wl on 2018/9/10.
 */
class LuckDrawProtocol {

    private val PATH_INITIATE_HISTORY = "/nonolive/gappserv/lottery/InitiateHistory"

    private val PATH_JOIN_HISTORY = "/nonolive/gappserv/lottery/joinHistory"

    /**
     * 测试接口: 获取参与抽奖
     */
    fun getTestRequest(page: Int, pageSize: Int,callback: StringCallback) {
        println(pageSize)
        val url = "http://wanandroid.com/tools/mockapi/10351/joinHistory${page}"
        val html = """
            {
                "code": 0,
                "body": {
                    "models": [{
                            "draw_id": "afef32432432affe23",
                            "user_id": 1,
                            "loginname": "xiaoming1",
                            "level": 80,
                            "avatar": "https://upload.jianshu.io/users/upload_avatars/5565867/eead6b83-452b-42a7-b77e-5dc9109d8a71.jpg",
                            "is_winner": 0,
                            "pay_success": 0,
                            "receiving_address": "广州市琶洲区xx某个地方1",
                            "join_success": 0,
                            "done_requirement_id": 888,
                            "create_time": 20180101,
                            "join_time": 20180101,
                            "lottery_time": 20180101
                        },
                        {
                            "draw_id": "afef32432432affe23",
                            "user_id": 2,
                            "loginname": "xiaoming2",
                            "level": 80,
                            "avatar": "https://upload.jianshu.io/users/upload_avatars/5565867/eead6b83-452b-42a7-b77e-5dc9109d8a71.jpg",
                            "is_winner": 0,
                            "pay_success": 0,
                            "receiving_address": "广州市琶洲区xx某个地方2",
                            "join_success": 0,
                            "done_requirement_id": 888,
                            "create_time": 20180101,
                            "join_time": 20180101,
                            "lottery_time": 20180101
                        }, {
                            "draw_id": "afef32432432affe23",
                            "user_id": 3,
                            "loginname": "xiaoming3",
                            "level": 80,
                            "avatar": "https://upload.jianshu.io/users/upload_avatars/5565867/eead6b83-452b-42a7-b77e-5dc9109d8a71.jpg",
                            "is_winner": 0,
                            "pay_success": 0,
                            "receiving_address": "广州市琶洲区xx某个地方3",
                            "join_success": 0,
                            "done_requirement_id": 888,
                            "create_time": 20180101,
                            "join_time": 20180101,
                            "lottery_time": 20180101
                        }, {
                            "draw_id": "afef32432432affe23",
                            "user_id": 4,
                            "loginname": "xiaoming4",
                            "level": 80,
                            "avatar": "https://upload.jianshu.io/users/upload_avatars/5565867/eead6b83-452b-42a7-b77e-5dc9109d8a71.jpg",
                            "is_winner": 0,
                            "pay_success": 0,
                            "receiving_address": "广州市琶洲区xx某个地方4",
                            "join_success": 0,
                            "done_requirement_id": 888,
                            "create_time": 20180101,
                            "join_time": 20180101,
                            "lottery_time": 20180101
                        },
                        {
                            "draw_id": "afef32432432affe23",
                            "user_id": 5,
                            "loginname": "xiaoming5",
                            "level": 80,
                            "avatar": "https://upload.jianshu.io/users/upload_avatars/5565867/eead6b83-452b-42a7-b77e-5dc9109d8a71.jpg",
                            "is_winner": 0,
                            "pay_success": 0,
                            "receiving_address": "广州市琶洲区xx某个地方5",
                            "join_success": 0,
                            "done_requirement_id": 888,
                            "create_time": 20180101,
                            "join_time": 20180101,
                            "lottery_time": 20180101
                        }
                    ],
                    "totalRows": 100
                }
            }
        """.trimIndent()
        callback.success(html)
    }
}

interface StringCallback {
    fun success(html: String)
    fun failed()
}