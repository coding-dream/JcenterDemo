package com.nono.android.modules.setting.luckdraw.bean

import org.live.baselib.rvsample.BaseEntity

/**
 * 发起抽奖实体
 */
data class InitiateHistory(
        val draw_id: String,
        val user_id: Int,
        val loginname: String,
        val level: Int,
        val avatar: String,
        val is_winner: Int,
        val pay_success: Int,
        val receiving_address: String,
        val join_success: Int,
        val done_requirement_id: Int,
        val create_time: Int,
        val join_time: Int,
        val lottery_time: Int
) : BaseEntity

/**
 * 参与抽奖实体
 */
data class ParticipateHistory(
        val draw_id: String,
        val user_id: Int,
        val loginname: String,
        val level: Int,
        val avatar: String,
        val is_winner: Int,
        val pay_success: Int,
        val receiving_address: String,
        val join_success: Int,
        val done_requirement_id: Int,
        val create_time: Int,
        val join_time: Int,
        val lottery_time: Int
) : BaseEntity
