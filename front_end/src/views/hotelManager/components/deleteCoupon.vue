<template>
    <a-modal
            :visible="deleteCouponVisible"
            title="删除优惠策略"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <!-- #点确定的响应⽅法handleSubmit -->
        <p>请填写要删除的优惠券ID：</p>
        <a-input
                v-model="toDeleteID"
        />
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import {deleteCouponAPI} from "../../../api/coupon";
    export default {
        name: 'deleteCouponModal',
        data() {
            return {
                toDeleteID : 0,
            }
        },
        computed: {
            ...mapGetters([
                'activeHotelId',
                'deleteCouponVisible',
            ])
        },
        methods: {
            ...mapMutations([
                'set_deleteCouponVisible'
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'deleteHotelCoupon'
            ]),
            cancel() {
                this.set_deleteCouponVisible(false)
            },
            handleSubmit(e) {
                const data = {
                    // 这里添加接口参数
                    couponID: this.toDeleteID
                }
                this.deleteHotelCoupon(data);
            },
        }
    }
</script>