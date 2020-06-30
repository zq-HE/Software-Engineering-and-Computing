<template>
    <a-modal
            :visible="addUserModalVisible"
            title="添加用户"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                        v-decorator="[
                    'email',
                    { rules: [{required: true, message: '请输入用户邮箱', }] }
                ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                        v-decorator="[
                    'password',
                    { rules: [{required: true, message: '请输入密码', }] }
                ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                        v-decorator="[
                    'username',
                    { rules: [{required: true, message: '请输入用户名', }] }
                ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="电话号码">
                <a-input
                        v-decorator="[
                    'phonenumber',
                    { rules: [{required: true, message: '请输入电话号码', }] }
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
    const moment = require('moment')
    export default {
        name: 'addUserModal',
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
                'addUserModalVisible',
                'UserList',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addUserModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addUserModalVisible',
                'set_addUserParams',
            ]),
            ...mapActions([
                'getUserList',
                'addUser',
            ]),
            cancel() {
                this.set_addUserModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            username: this.form.getFieldValue('username'),
                            phonenumber: this.form.getFieldValue('phonenumber'),
                            birthday: this.birthday,
                            credit: 1000,
                        }
                        this.set_addUserParams(data)
                        this.addUser()
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