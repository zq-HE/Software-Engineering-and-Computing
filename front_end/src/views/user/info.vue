<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item v-if="!modify" label="会员类型" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-select v-if="modifyVip" default-value="请选择" style="width: 120px" @change="selectVipType">
                            <a-select-option value="1">
                                普通会员
                            </a-select-option>
                            <a-select-option value="2">
                                企业会员
                            </a-select-option>
                        </a-select>
                        <span v-else>{{ userInfo.vipType }}</span>
                    </a-form-item>

                    <a-form-item v-if="(!modify)&&(((!modifyVip)&&userInfo.vipType==='企业会员')||(modifyVip&&(!isNormalVip)))" label="企业名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写信息"
                            v-model="vipMsg"
                            v-if="modifyVip"
                            style="width:40%"
                        />
                        <span v-else>{{ userInfo.vipInfo }}</span>
                    </a-form-item>

                    <a-form-item v-if="!modifyVip" label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item v-if="!modifyVip" label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>
                    
                    <a-form-item  v-if="!modifyVip" label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' },{ validator: this.checkNumber }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>

                    <a-form-item  v-if="!modifyVip" label="生日" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-date-picker
                                @change="onChange"
                                placeholder="请选择生日"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.birthday.substr(0,10)}}</span>
                    </a-form-item>

                    <a-form-item v-if="!modifyVip"  label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                            placeholder="请输入新密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                     <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                         <a-button v-if="modifyVip" type="primary" @click="submitVipInfo">
                             确认申请
                         </a-button>
                         <a-divider type="vertical"></a-divider>
                         <a-button v-if="modifyVip" type="dangerous" @click="cancelSubVip">
                             取消申请
                         </a-button>
                         <a-divider type="vertical"></a-divider>
                        <a-button v-if="!modifyVip" type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                         <a-divider type="vertical"></a-divider>
                         <a-button v-if="!modifyVip" type="primary" @click="registerUserVip">
                             申请会员
                         </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2">
                <a-table
                    :columns="columns"
                    :dataSource="userOrderList"
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
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
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

                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record.id)"
                            @cancel="cancelCancelOrder"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState == '已执行'"></a-divider>
                        <a-popover title="评价" v-if="record.orderState == '已执行'">
                            <a slot="content">
                                <span>
                                    <a-rate v-model="value" :tooltips="desc" @change="confirmscoreOrder(value,record.id)"/>
                                    <span class="ant-rate-text">
                                        {{ desc[value - 1] }}
                                    </span>
                                </span>
                            </a>
                            <a-button type="normal" size="small">评价</a-button>
                        </a-popover>
                        
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="信用值变更记录" key="3">
                            <a-table
                                    :columns="columns2"
                                    :dataSource="creditRecord"
                                    size="middle"
                                    bordered>
                                <span slot="changeValue" slot-scope="text" >
                                    <a-tag :color="text < 0 ? 'volcano' : 'green'">
                      {{ text }}
                  </a-tag>
                                </span>
                            </a-table>
                        </a-tab-pane>
        </a-tabs>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import {getCreditRecordAPI, registerAPI, registerVipAPI} from "../../api/user";
const columns2=[
    {
        title:'记录号',
        dataIndex:'id',

    },
    {
        title: '改变值',
        dataIndex: 'changeValue',
        scopedSlots: { customRender: 'changeValue' },

    },
    {
        title: '剩余值',
        dataIndex: 'restValue',
    },
    {
        title: '改变时间',
        dataIndex: 'changeTime',
        ellipsis: true,

    },
    {
        title: '改变原因',
        dataIndex: 'reason',
        ellipsis: true,

    }
];
const columns = [
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
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
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
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' },
            {text: '已执行',value:'已执行'},{text: '已评价',value:'已评价'}],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
    
  ];
export default {
    name: 'info',
    data(){
        return {
            birthday:"",
            isNormalVip: true,
            vipT: "",
            vipMsg: "",
            modifyVip: false,
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns2,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
            value:3,
            desc: ['terrible', 'bad', 'normal', 'good', 'wonderful'],
        }
    },
    components: {
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'creditRecord'
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
        await this.getCreditRecord()
    },
    methods: {
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'cancelOrder',
            'getCreditRecord',
            'registerVip',
            'scoreOrder'
        ]),
        checkNumber(rule, value, callback) {
            const re = /^[0-9]+$/
            if (re.test(value)) {
                callback();
            } else {
                callback(new Error('请输入数字'));
            }
            callback()
        },
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password'),
                        birthday: this.birthday,
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                    'password':this.userInfo.password,
                    'birthday':this.userInfo.birthday.substr(0,10),
                })
            }, 0)
            this.modify = true
        },
        registerUserVip(){
            this.modifyVip = true
        },
        selectVipType(val){
            if(val==="1"){
                this.vipT = "普通会员"
                this.isNormalVip = true
            }else if(val==="2"){
                this.vipT = "企业会员"
                this.isNormalVip = false
            }
        },
        submitVipInfo(){
            const data = {
                vipType: this.vipT,
                vipInfo: this.vipMsg
            }
            this.registerVip(data)
            this.modifyVip = false
            this.vipMsg = ""
            this.vipT = ""
            this.isNormalVip = true
        },
        cancelSubVip(){
            this.modifyVip = false
            this.vipMsg = ""
            this.vipT = ""
            this.isNormalVip = true
        },
        cancelModify() {
            this.modify = false
        },
        confirmCancelOrder(orderId){
            this.cancelOrder(orderId)
        },
        cancelCancelOrder() {

        },
        confirmscoreOrder(value,orderId){
            this.scoreOrder({
                value:value,
                orderId:orderId
            })
        },
        onChange(date, dateString) {
            console.log(date, dateString);
            this.birthday=dateString;
        },

    },

}
</script>
<style scoped lang="less">
    .info-wrapper {
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
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>