<template> 
    <div>
        <div class="line_margin_top"></div>
        <el-row>
            <el-col :span="18">
               <el-dropdown class="float_right"  @command="handleCommand">
                    <el-button>
                        {{ $t("message.global.more_func") }}<i class="el-icon-caret-bottom el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown">                     
                        <el-dropdown-item command="d">{{ $t("message.fund.delete") }}</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-col>                            
        </el-row>
        <el-row>
            <el-col :offset="2" :span="20">
                <fund-info></fund-info>
            </el-col>            
        </el-row>
        <el-row>
            <el-col :offset="2" :span="20">
                <fund-account></fund-account>
            </el-col>            
        </el-row>
        <el-row>
            <el-col :offset="2" :span="20">
                <fund-crea-account></fund-crea-account>
            </el-col>            
        </el-row>
    </div>
</template>
<script>
    import FundInfo from "./FundInfo.vue"
    import FundAccount from "./FundAccount.vue"
    import FundCrecaAccount from "./FundCrecaAccount.vue"
    export default{
        data(){
            return {
                fundAccontList: [],
                loading: true
            }
        },
        components: {
			"fund-info":FundInfo,
            "fund-account":FundAccount,
            "fund-crea-account":FundCrecaAccount
		},
        methods:{
          handleCommand(command){
                var _self = this;
                if (command=='d'){
                        this.$confirm(_self.$t('message.fund.delete_confim'),
                            _self.$t('message.global.prompt'), {
                        confirmButtonText: _self.$t('message.global.confirm'),
                        cancelButtonText: _self.$t('message.global.cancel'),
                        type: 'warning'
                    }).then(() => {
                            _self.winsigns.ds.DELETE({url:_self.winsigns.api.fundURL.funds+'/'+_self.$route.params.fundId},
                        function(data){                                          
                            //删除成功
                            _self.$message({
                                message: _self.$t('message.global.success'),
                                type: 'success'
                            });
                            _self.$router.push({ name: 'Fund', params: {}})
                        },function(data){
                            _self.$message.error(_self.$t('message.global.fail'));
                        })                        
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: _self.$t('message.global.cancel_delete')
                        });          
                    });
                }                
            }        
        }
    }
</script>
<style scoped>   
    .el-button,.el-dropdown-menu{
        width:150px
    }
</style>