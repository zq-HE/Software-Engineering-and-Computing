<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px;height:300px">
                        <img
                            alt="example"
                            src="http://cos.nju.world:9000/public-pictures/NJU_北大楼.jpg"
                            slot="cover"
                            style="height: 300px"
                            referrerPolicy="no-referrer"
                            />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.phoneNum">
                            <span class="label">电话</span>
                            <span class="value">{{ currentHotelInfo.phoneNum }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span> 
                            <span class="value">{{ currentHotelInfo.rate | numFilter }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span> 
                            <a-rate style="font-size: 15px" :value="this.starStrToNum(currentHotelInfo.hotelStar)" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span> 
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="细节展示" key="2">
                        <div class="slide">
                            <p class="des">{{ currentHotelInfo.description}}</p>
                        <a-carousel autoplay>
                            <div>
                                <img
                                    alt="example"
                                    src="@/assets/detail2.jpg"
                                    referrerPolicy="no-referrer"
                            />
                            </div>
                            <div>
                                <img
                                        alt="example"
                                        src="@/assets/detail1.jpg"
                                        referrerPolicy="no-referrer"
                                />
                            </div>
                            <div>
                                <img
                                        alt="example"
                                        src="@/assets/detail3.jpg"
                                        referrerPolicy="no-referrer"
                                />
                            </div>
                            <div>
                                <img
                                        alt="example"
                                        src="@/assets/detail4.jpg"
                                        referrerPolicy="no-referrer"
                                />
                            </div>
                        </a-carousel>
                    </div>
                    </a-tab-pane>

                    <a-tab-pane tab="历史订单" key="3">
                        <MyOrderList></MyOrderList>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import RoomList from './components/roomList'
import MyOrderList from './components/myOrderList'
export default {
    name: 'hotelDetail',
    components: {
        RoomList,
        MyOrderList,
    },
    data() {
        return {

        }
    },
    computed: {
        ...mapGetters([
            'currentHotelInfo',
        ])
    },
    mounted() {
        this.set_currentHotelId(Number(this.$route.params.hotelId))
        this.getHotelById()
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
        ]),
        ...mapActions([
            'getHotelById'
        ]),
        starStrToNum(v){
            if(v==="One"){
                return 1
            }else if(v==="Two"){
                return 2
            }else if(v==="Three"){
                return 3
            }else if(v==="Four"){
                return 4
            }else if(v==="Five"){
                return 5
            }
            return 2
        }
    },
    filters: {
        numFilter (value) {
            let realVal = ''
            if (!isNaN(value) && value!== '') {
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
    .hotelDetailCard {
        padding: 50px 50px;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>
<style scoped>
    /* For demo */
    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 400px;
        line-height: 400px;
        background: #364d79;
        overflow: hidden;
    }
    .slide{
        width: 60%;
    }
    .des{
        font-size: 18px;
    }
    .ant-carousel >>> .slick-slide h3 {
        color: #ffffff;
    }
    .ant-carousel >>> .slick-slide img {
        width: 100%;
        height: 100%;
    }
</style>