<template>
    <a-modal
        :visible="updateManagerModalVisible"
        title="更改信息"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
    <a-Form :form="form">
        <!--<a-form-item v-bind="formItemLayout" label="用户邮箱">
            <a-input
                v-decorator="[
                    'email',
                    { initialValue: currentManager.email,
                        rules: [{required: true, message: '请输入用户邮箱', }] }
                ]"
            />
        </a-form-item>-->
        <a-form-item v-bind="formItemLayout" label="密码">
            <a-input
                v-decorator="[
                    'password',
                    { initialValue: currentManager.password,
                        rules: [{required: true, message: '请输入密码', }] }
                ]"
                />
        </a-form-item >
        <a-form-item v-bind="formItemLayout" label="用户名">
            <a-input
                v-decorator="[
                    'username',
                    { initialValue: currentManager.userName,
                        rules: [{required: true, message: '请输入用户名', }] }
                ]"
                />
        </a-form-item >
        <a-form-item v-bind="formItemLayout" label="电话号码">
            <a-input
                v-decorator="[
                    'phonenumber',
                    { initialValue: currentManager.phoneNumber,
                        rules: [{required: true, message: '请输入电话号码', },{ validator: this.checkNumber }] }
                ]"
                />
        </a-form-item >
        <a-form-item v-bind="formItemLayout" label="生日">
            <a-date-picker
                    @change="onChange"
                    placeholder="请选择生日"
            />
        </a-form-item >
    </a-Form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'updateManagerModal',
    data() {
        return {
            birthday:"",
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
            'updateManagerModalVisible',
            'currentManager',
            'managerList',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'updateManagerModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_updateManagerModalVisible',
            'set_updateManagerParams',
        ]),
        ...mapActions([
            'getManagerList',
            'updateManager',
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
        cancel() {
            this.set_updateManagerModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        id:this.currentManager.id,
                        email: this.form.getFieldValue('email'),
                        password: this.form.getFieldValue('password'),
                        username: this.form.getFieldValue('username'),
                        phonenumber: this.form.getFieldValue('phonenumber'),
                        birthday:this.birthday
                    }
                    this.set_updateManagerParams(data)
                    this.updateManager()
                }
            });
        },
        onChange(date, dateString) {
            console.log(date, dateString);
            this.birthday=dateString;
        },
    }
}
</script>