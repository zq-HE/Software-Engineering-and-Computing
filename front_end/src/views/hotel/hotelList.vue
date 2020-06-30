<template>
    <div class="hotelList">

        <a-layout>
            <a-layout-content style="min-width: 800px">
                <a-spin :spinning="hotelListLoading">
                    <div>
                        <a-select default-value="请选择展示分类" style="width: 11%" @change="handleStatusVisible">
                            <a-select-option value="1">
                                展示全部酒店
                            </a-select-option>
                            <a-select-option value="2">
                                展示曾预订酒店
                            </a-select-option>
                            <a-select-option value="3">
                                展示曾执行酒店
                            </a-select-option>
                            <a-select-option value="4">
                                展示曾取消酒店
                            </a-select-option>
                        </a-select>
                        <a-divider type="vertical"></a-divider>
                        <!--
                        <a-button type="primary" @click="changeBookDisplay">{{bookDisplayMsg}}</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="primary" @click="changeCancelDisplay">{{cancelDisplayMsg}}</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="primary" @click="changeExecDisplay">{{execDisplayMsg}}</a-button>
                        <a-divider type="vertical"></a-divider>
                        -->
                        <a-select default-value="默认排序" style="width: 8%" @change="handleChange">
                            <a-select-option value="1">
                                默认排序
                            </a-select-option>
                            <a-select-option value="2">
                                星级降序
                            </a-select-option>
                            <a-select-option value="3">
                                评分降序
                            </a-select-option>
                        </a-select>
                        <a-divider type="vertical"></a-divider>
                        <a-input-search style="width: 20%" placeholder="请输入搜索内容" enter-button @search="onSearch" />
                    </div>
                    <div class="card-wrapper">
                        <HotelCard :hotel="item" :showAll="showAll" :hasCanceledVisible="hasCanceledVisible" :hasExecedVisible="hasExecedVisible" :hasBookedVisible="hasBookedVisible" v-for="item in currentHotelList" :class="item.name.indexOf(searchingStr)!==-1?'':'hidden'" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                        <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                        </div>
                        <a-pagination showQuickJumper :total="currentHotelList.totalElements" :defaultCurrent="1" @change="pageChange(userId)"></a-pagination>
                    </div>
                </a-spin>
            </a-layout-content>
        </a-layout>
    </div>
</template>
<script>
    import HotelCard from './components/hotelCard'
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import user from "../../store/modules/user";

    export default {
        name: 'home',
        components: {
            HotelCard
        },
        data(){
            return{
                emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
                currentHotelList:[],
                showAll: true,
                hasBookedVisible: false,
                hasCanceledVisible: false,
                hasExecedVisible: false,
                bookDisplayMsg : "关闭预订状态展示",
                execDisplayMsg : "关闭执行状态展示",
                cancelDisplayMsg: "关闭撤销状态展示",
            }
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'hotelList',
                'hotelListLoading',
                'searchingStr'
            ])
        },
        async mounted() {
            await this.getUserInfo()
            await this.getHotelList(user.state.userId)
            this.currentHotelList=this.hotelList
        },
        methods: {
            ...mapMutations([
                'set_hotelList',
                'set_hotelListParams',
                'set_hotelListLoading',
                'set_searchingStr'
            ]),
            ...mapActions([
                'getUserInfo',
                'updateUserInfo',
                'getHotelList'
            ]),

            changeBookDisplay(){
                // HotelCard.hasBookedVisible = !HotelCard.hasBookedVisible;
                this.hasBookedVisible = !this.hasBookedVisible;
                this.bookDisplayMsg = this.hasBookedVisible ? "关闭预订状态展示" : "打开预订状态展示";

            },

            changeExecDisplay(){
                this.hasExecedVisible = !this.hasExecedVisible;
                this.execDisplayMsg = this.hasExecedVisible ? "关闭执行状态展示" : "打开执行状态展示";
            },

            changeCancelDisplay(){
                this.hasCanceledVisible = !this.hasCanceledVisible;
                this.cancelDisplayMsg = this.hasCanceledVisible ? "关闭撤销状态展示" : "打开撤销状态展示";
                // alert("SHIW");
            },

            handleStatusVisible(val){
                if(val==="1"){
                    this.showAll = true
                    this.hasBookedVisible = false
                    this.hasExecedVisible = false
                    this.hasCanceledVisible = false
                }else if(val==="2"){
                    this.showAll = false
                    this.hasBookedVisible = true
                    this.hasExecedVisible = false
                    this.hasCanceledVisible = false
                }else if(val==="3"){
                    this.showAll = false
                    this.hasBookedVisible = false
                    this.hasExecedVisible = true
                    this.hasCanceledVisible = false
                }else if(val==="4"){
                    this.showAll = false
                    this.hasBookedVisible = false
                    this.hasExecedVisible = false
                    this.hasCanceledVisible = true
                }
            },

            pageChange(page, pageSize, userId) {
                const data = {
                    pageNo: page - 1
                }
                this.set_hotelListParams(data)
                this.set_hotelListLoading(true)
                this.getHotelList(userId)
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            onSearch(value) {
                this.set_searchingStr(value)
            },
            handleChange(e) {
                let list = this.hotelList
                if(e==='2'){
                    for(let i in list){
                        for(let j in list){
                            if(list[i].hotelStar<list[j].hotelStar){
                                let temp=list[i]
                                list[i]=list[j]
                                list[j]=temp
                            }
                        }
                    }
                    this.currentHotelList=list
                    this.$forceUpdate();

                }
                else if(e==='3'){
                    for(let i in list){
                        for(let j in list){
                            if(list[i].rate>list[j].rate){
                                let temp=list[i]
                                list[i]=list[j]
                                list[j]=temp
                            }
                        }
                    }
                    this.currentHotelList=list
                    this.$forceUpdate();

                }
            }
        }
    }
</script>
<style scoped lang="less">
    .hotelList {
        text-align: center;
        padding: 50px 0;
        .emptyBox {
            height: 0;
            margin: 10px 10px
        }
        .card-wrapper{
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            min-height: 800px
        }
        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }
    }
</style>
<style>
    .hidden{
        display: none;
    }
</style>