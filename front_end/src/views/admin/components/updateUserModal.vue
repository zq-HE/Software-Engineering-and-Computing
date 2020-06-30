<template>
    <a-modal
            :visible="updateUserModalVisible"
            title="修改信息"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <!--<a-form-item v-bind="formItemLayout" label="修改邮箱">
                <a-input
                        v-decorator="[
                    'email',
                ]"
                />
            </a-form-item>-->
            <a-form-item v-bind="formItemLayout" label="修改密码">
                <a-input
                        v-decorator="[
                    'password',
                        { initialValue: currentUser.password,
                        rules: [{required:true,message: '请输入密码', }]}
                ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="修改用户名">
                <a-input
                        v-decorator="[
                    'username',
                    { initialValue: currentUser.userName,
                        rules: [{required:true,message: '请输入用户名', }]}
                ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="修改电话号码">
                <a-input
                        v-decorator="[
                    'phonenumber',
                    { initialValue: currentUser.phoneNumber,
                        rules: [{required:true,message: '请输入电话号码', },{ validator: this.checkNumber }] }
                ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="修改生日">
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
        name: 'updateUserModal',
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
                'updateUserModalVisible',
                'userList',
                'currentUser',
                'activeUserId',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'updateUserModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_updateUserModalVisible',
                'set_updateUserParams',
            ]),
            ...mapActions([
                'getUserList',
                'updateUser',
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
                this.set_updateUserModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            id:this.activeUserId,
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            username: this.form.getFieldValue('username'),
                            phonenumber: this.form.getFieldValue('phonenumber'),
                            birthday: this.birthday,
                        }
                        this.set_updateUserParams(data)
                        this.updateUser()
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