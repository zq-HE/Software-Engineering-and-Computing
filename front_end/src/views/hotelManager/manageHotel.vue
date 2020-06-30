<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel">
                        <a-icon type="plus"/>
                        添加酒店
                    </a-button>
                    <a-divider type="vertical"></a-divider>
                    <a-button type="primary" @click="addGlobalCoupon">
                        <a-icon type="plus"/>
                        添加全局优惠规则
                    </a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
                        bordered
                >
                    <span slot="rate" slot-scope="text">
                        <span>{{ text|numFilter }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="addRoom(record)">录入房间</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="dashed" size="small" @click="manageInfo(record)">酒店信息管理</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="confirmDeleteHotel(record.id)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-table
                        :columns="columns2"
                        :dataSource="orderList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <!--这里传入的record就是当前订单对象！！-->
                    <span slot="action" slot-scope="record">

                        <a-popover title="其他情况">
                              <template slot="content">

                                  <div>有无儿童：{{record.haveChild?"有":"无"}}</div>
                                  <div>创建日期：{{record.createDate}}</div>
                                  <div>客户姓名：{{record.clientName}}</div>
                                  <div>客户电话：{{record.phoneNumber}}</div>
                                  <div>订单状态：{{record.orderState}}</div>

                              </template>
                            <a-button type="primary" size="small">订单详情</a-button>
                        </a-popover>

                        <a-divider type="vertical"></a-divider>

                        <a-popconfirm
                                title="确定想删除该订单吗？"
                                @confirm="confirmDeleteOrder(record.id)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已撤销'"
                        >
                            <a-button type="danger" size="small">删除订单</a-button>
                        </a-popconfirm>

                        <a-popconfirm
                                title="确定执行该订单吗？"
                                @confirm="comfirmDoOrder(record.id)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已预订'"
                        >
                            <a-button size="small">执行订单</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <Info></Info>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddHotelModal from './components/addHotelModal'
    import AddRoomModal from './components/addRoomModal'
    import Coupon from './components/coupon'
    import addCoupon from "./components/addCoupon"
    import Info from './components/Info'

    const moment = require('moment')
    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar'
        },
        {
            title: '评分',
            dataIndex: 'rate',
            scopedSlots: {customRender: 'rate'}
        },
        {
            title: '简介',
            dataIndex: 'description',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns2 = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'}
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'}
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已评价', value: '已评价'},
                {text: '已执行', value: '已执行'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageHotel',
        data() {
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns1,
                columns2,
                form: this.$form.createForm(this, {name: 'manageHotel'}),
            }
        },
        components: {
            AddHotelModal,
            AddRoomModal,
            Coupon,
            Info
        },
        computed: {
            ...mapGetters([
                'orderList',
                'hotelList',
                'addHotelModalVisible',
                'addRoomModalVisible',
                'activeHotelId',
                'couponVisible',
                'InfoVisible'
            ]),
        },
        async mounted() {
            await this.getHotelList()
            await this.getAllOrders()
        },
        methods: {
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_addRoomModalVisible',
                'set_couponVisible',
                'set_activeHotelId',
                'set_addCouponVisible',
                'set_InfoVisible'
            ]),
            ...mapActions([
                'getHotelList',
                'getAllOrders',
                'getHotelCoupon',
                'doOrder',
                'deleteOrder',
                'deleteHotel',
                'getHotelInfo'
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true)
            }
            ,
            addRoom(record) {
                this.set_activeHotelId(record.id)
                this.set_addRoomModalVisible(true)
            }
            ,
            showCoupon(record) {
                this.set_activeHotelId(record.id)
                this.set_couponVisible(true)
                this.getHotelCoupon()
            },
            manageInfo(record){
                this.set_activeHotelId(record.id)
                this.set_InfoVisible(true)
                this.getHotelInfo(record.id)
            }
            ,

            comfirmDoOrder(orderId) {
                this.doOrder(orderId)
            }
            ,
            confirmDeleteHotel(id) {
                this.deleteHotel(id)
            }
            ,
            confirmDeleteOrder(orderId) {
                this.deleteOrder(orderId)
            }
            ,

            addGlobalCoupon() {
                this.set_activeHotelId(0)
                this.set_addCouponVisible(true)
            }
        },
        filters: {
            numFilter(value) {
                let realVal = ''
                if (!isNaN(value) && value !== '') {
                    // 截取当前数据到小数点后两位
                    realVal = parseFloat(value).toFixed(2)
                } else {
                    realVal = '--'
                }
                return realVal
            }
        }
    }
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>