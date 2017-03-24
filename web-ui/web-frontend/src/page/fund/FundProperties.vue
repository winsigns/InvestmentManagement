<template> 
    <div>
        <div class="line_margin_top"></div>
        <el-row>
            <el-col :span="18">
               <el-dropdown class="float_right"  @command="handleCommand">
                    <el-button>
                        更多功能<i class="el-icon-caret-bottom el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown">                     
                        <el-dropdown-item command="d">删除基金产品</el-dropdown-item>                                                 
                    </el-dropdown-menu>
                </el-dropdown>
            </el-col>                            
        </el-row>
        <el-row>
            <el-col :offset="5" :span="19">
                <fund-info></fund-info>
            </el-col>            
        </el-row>
        <el-row>
            <el-col :offset="5" :span="19">
                <fund-account></fund-account>
            </el-col>            
        </el-row>
        <el-row>
            <el-col :offset="5" :span="19">
                <fund-crea-account></fund-crea-account>
            </el-col>            
        </el-row>
    </div>
</template>
<script>
    import FundInfo from "./FundInfo.vue"
    import FundAccount from "./FundAccount.vue"
    import FundCrecaAccount from "./FundCrecaAccount.vue"
    import api from '../../config/api.json'
    import ds from '../../common/ds'
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
                        this.$confirm('您正在删除基金 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        ds.DELETE({url:api.fundURL.funds+'/'+_self.$route.params.fundId},
                        function(data){                                          
                            //删除成功
                            _self.$message({
                                message: '删除成功',
                                type: 'success'
                            });
                            _self.$router.push({ name: 'Fund', params: {}})
                        },function(data){
                            _self.$message.error('删除基金产品失败');
                        })                        
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
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