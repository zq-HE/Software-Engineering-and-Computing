<template>
    <div>
        <a-modal
                :visible="couponVisible"
                title="优惠策略"
                width="900px"
                :footer="null"
                @cancel="cancel"
        >
            <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
            <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="dangerous" @click="deleteCoupon">
                    <a-icon type="minus"/>
                    删除优惠策略
                </a-button>
                <a-divider type="vertical"></a-divider>
                <a-button type="primary" @click="addCoupon">
                    <a-icon type="plus"/>
                    添加优惠策略
                </a-button>
            </div>
            <a-table
                    :columns="columns"
                    :dataSource="couponList"
                    bordered
            >
                <a-tag color="red" slot="couponName" slot-scope="text">
                    {{text}}
                </a-tag>
            </a-table>
        </a-modal>
        <AddCoupon></AddCoupon>
        <DeleteCouponModal></DeleteCouponModal>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddCoupon from './addCoupon'
    import DeleteCouponModal from './deleteCoupon'
    const columns = [
        // 这里定义列表头
        {
            title: '优惠类型',
            dataIndex: 'couponType',
        },
        {
            title: '优惠卷名称',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'}
        },
        {
            title: '折扣',
            dataIndex: 'discount',
        },
        {
            title: '优惠简介',
            dataIndex: 'description',

        },
        {
            title: '优惠⾦额',
            dataIndex: 'discountMoney',
        },
        {
            title: '优惠券ID',
            dataIndex: 'id',
        },
    ];
    //let newCouponList=this.couponList
    export default {
        name: 'coupon',
        data() {
            return {
                columns,
                //newCouponList
            }
        },
        components: {
            DeleteCouponModal,
            AddCoupon,
        },
        computed: {
            ...mapGetters([
                'couponVisible',
                'couponList',
            ])
        },
        /*mounted() {
            for(let index in newCouponList){
                if(newCouponList[index].couponType===3){
                    newCouponList[index].couponType="满减特惠"
                }
                else if(newCouponList[index].couponType===1){
                    newCouponList[index].couponType="生日特惠"
                }
                else if(newCouponList[index].couponType===2){
                    newCouponList[index].couponType="多间特惠"
                }
                else if(newCouponList[index].couponType===4){
                    newCouponList[index].couponType="限时特惠"
                }
            }
        },*/
        methods: {
            ...mapMutations([
                'set_addCouponVisible',
                'set_couponVisible',
                'set_deleteCouponVisible',
            ]),
            ...mapActions([
                'getHotelCoupon'
            ]),
            cancel() {
                this.set_couponVisible(false)
            },
            addCoupon() {
                this.set_addCouponVisible(true)
                this.set_couponVisible(false)
            },
            deleteCoupon() {
                this.set_deleteCouponVisible(true)
                this.set_couponVisible(false)
            }
        },
    }
</script>
<style scoped>

</style>