<template>
    <a-modal
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <!-- #点确定的响应⽅法handleSubmit -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
 'type',
 { rules: [{ required: true, message: '请选择类型' }] }]"
                        @change="changeType"
                >
                    <a-select-option value="1">⽣⽇特惠</a-select-option>
                    <a-select-option value="2">多间特惠</a-select-option>
                    <a-select-option value="3">满减特惠</a-select-option>
                    <a-select-option value="4">限时特惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写券名"
                        v-decorator="['name', { rules: [{ required: true, message:
'请输⼊券名' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description', { rules: [{ required: true,
message: '请填写优惠简介' }] }]"
                />
            </a-form-item>
            <a-form-item label="起始日期" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='4'">
                <a-date-picker
                        @change="onChange"
                        placeholder="请选择起始日期"
                        v-decorator="['startTime', { rules: [{ required: true,
message: '请选择起始日期' }] }]"
                />
            </a-form-item>
            <a-form-item label="结束日期" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='4'">
                <a-date-picker
                        @change="onChange"
                        placeholder="请选择结束日期"
                        v-decorator="['endTime', { rules: [{ required: true,
message: '请选择结束日期' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标⾦额" v-if="this.form.getFieldValue('type')==='3'">
                <a-input
                        placeholder="请填写达标⾦额"
                        v-decorator="['targetMoney', { rules: [{ required: true,
message: '请填写达标⾦额' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标房间" v-if="this.form.getFieldValue('type')==='2'">
                <a-input
                        placeholder="请填写达标房间"
                        v-decorator="['targetRoom', { rules: [{ required: true,
message: '请填写达标房间' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠⾦额" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写优惠⾦额"
                        v-decorator="['discountMoney', { rules: [{ required: true,
message: '请填写优惠⾦额' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import {message} from "ant-design-vue";
export default {
    name: 'addCouponModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    computed: {
        ...mapGetters([
            'activeHotelId',
            'addCouponVisible',
        ])
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible'
        ]),
        ...mapActions([
            // addHotelCoupon：添加酒店策略接口
            'addHotelCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
        },
        changeType(v){
            if( v === '3') {
                this.$message.success('请填写满减特惠')
            }
            else if( v==='4'){
                this.$message.success('请填写限时特惠')
            }
            else if( v==='2'){
                this.$message.success('请填写多间特惠')
            }
            else{
                this.$message.success('请填写生日特惠')
            }
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    if (this.form.getFieldValue('startTime')>this.form.getFieldValue('endTime') && this.form.getFieldValue('type')==='4') {
                        message.error('起始日期不得晚于结束日期！')
                    } else{
                        if (this.form.getFieldValue('discountMoney') > 0) {
                            if (this.form.getFieldValue('type') === '4') {
                                const data = {
                                    // 这里添加接口参数
                                    name: this.form.getFieldValue('name'),
                                    description: this.form.getFieldValue('description'),
                                    startTime: this.form.getFieldValue('startTime'),
                                    endTime: this.form.getFieldValue('endTime'),
                                    type: Number(this.form.getFieldValue('type')),
                                    discountMoney:
                                        Number(this.form.getFieldValue('discountMoney')),
                                    hotelId: Number(this.activeHotelId),
                                    status: 1,
                                }

                                this.addHotelCoupon(data)
                            } else if (this.form.getFieldValue('type') === '3') {
                                const data = {
                                    // 这里添加接口参数
                                    name: this.form.getFieldValue('name'),
                                    description: this.form.getFieldValue('description'),
                                    type: Number(this.form.getFieldValue('type')),
                                    targetMoney:
                                        Number(this.form.getFieldValue('targetMoney')),
                                    discountMoney:
                                        Number(this.form.getFieldValue('discountMoney')),
                                    hotelId: Number(this.activeHotelId),
                                    status: 1,
                                }

                                this.addHotelCoupon(data)
                            } else if (this.form.getFieldValue('type') === '2') {
                                const data = {
                                    // 这里添加接口参数
                                    name: this.form.getFieldValue('name'),
                                    description: this.form.getFieldValue('description'),
                                    type: Number(this.form.getFieldValue('type')),
                                    targetRoom:
                                        Number(this.form.getFieldValue('targetRoom')),
                                    discountMoney:
                                        Number(this.form.getFieldValue('discountMoney')),
                                    hotelId: Number(this.activeHotelId),
                                    status: 1,
                                }

                                this.addHotelCoupon(data)
                            } else {
                                const data = {
                                    // 这里添加接口参数
                                    name: this.form.getFieldValue('name'),
                                    description: this.form.getFieldValue('description'),
                                    startTime: this.form.getFieldValue('startTime'),
                                    endTime: this.form.getFieldValue('endTime'),
                                    type: Number(this.form.getFieldValue('type')),
                                    discountMoney:
                                        Number(this.form.getFieldValue('discountMoney')),
                                    hotelId: Number(this.activeHotelId),
                                    status: 1,
                                }

                                this.addHotelCoupon(data)
                            }
                        } else {
                            message.error('优惠金额必须大于0')
                        }
                    }
                }
            });
        },
    }
}
</script>