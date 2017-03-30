<template> 
    <div>
        <el-row>        
            <div class="line_bottom">
                <el-button type="text" class="float_right right_button"
                @click="openDialog(row)">{{ $t("message.fund.account_add") }}</el-button>
                <h1>{{ $t("message.fund.account") }}</h1>
            </div>                
            <div class="line_margin_top"></div>            
        </el-row>
        <el-row>        
            <el-col :span="18" :offset="3">                 
                <el-table v-loading="loading" :data="fundAccontList" style="width: 100%" stripe>
                    <el-table-column sortable
                        prop="name"
                        :label=" $t('message.fund.account_name') ">
                    </el-table-column>   
                    <el-table-column :label=" $t('message.global.operation') ">
                        <template scope="scope">
                            <el-button
                            size="small"
                            @click="handleEdit(scope.$index, scope.row)">{{ $t("message.global.edit") }}</el-button>
                            <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">{{ $t("message.global.delete") }}</el-button>
                        </template>
                    </el-table-column>                 
                </el-table>
            </el-col>	                     
        </el-row>  

        <el-dialog :title="dlg.dlgTitle" v-model="dlg.dlgVisible" size="tiny">
            <el-form :model="dlg">
                <el-form-item :label=" $t('message.fund.account_name') " label-width="100px">
                     <el-input v-model="dlg.dlgFundAccountName"></el-input>
                </el-form-item>               
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dlg.dlgVisible=false">{{ $t("message.global.cancel") }}</el-button>
                <el-button @click="postFundAccount()">{{ $t("message.global.confirm") }}</el-button>
            </div>
        </el-dialog>        
    </div>
</template>
<script>
    export default{
        data(){
            return {
                fundAccontList: [],
                loading: true,
                //对话框  
                dlg:{
                    dlgTitle: '',
                    dlgFundAccountId:'',
                    dlgFundAccountName: '',
                    dlgVisible: false
                }                              
            }
        },
        mounted: function(){  
            this.getFundAccounts();                                           
        },
        methods:{
            getFundAccounts: function(){
                var _self = this;
                _self.winsigns.ds.GET({url:_self.winsigns.api.fundURL.funds + '/'+_self.$route.params.fundId+'/fund-accounts',
                        data:{}},function(data){
                    _self.loading = false;
                    _self.fundAccontList = data._embedded?data._embedded["fund-accounts"]:[];                                                                    
                })  
            },
            openDialog: function(){
                this.dlg.dlgTitle = this.$t('message.fund.account_add'),
                this.dlg.dlgVisible = true; 
                this.dlg.dlgFundAccountId="";
                this.dlg.dlgFundAccountName="";              
            },
            postFundAccount: function(){
                var _self = this;
                if (_self.dlg.dlgFundAccountId==''){//新增
                    _self.winsigns.ds.POST({url:_self.winsigns.api.fundURL.funds + '/'+_self.$route.params.fundId+'/fund-accounts',
                        data:{"name":_self.dlg.dlgFundAccountName}},function(data){
                        _self.getFundAccounts(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundAccountId="";
                        _self.dlg.dlgFundAccountName="";
                        _self.$message({
                            message: _self.$t('message.global.success'),
                            type: 'success'
                        });                                             
                    },function(err){
                        
                    })  
                } else{//修改
                    _self.winsigns.ds.PUT({url:_self.winsigns.api.fundURL.fundaccounts+'/'+_self.dlg.dlgFundAccountId,
                        data:{"name":_self.dlg.dlgFundAccountName}},function(data){
                        _self.getFundAccounts(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundAccountName="";
                        _self.dlg.dlgFundAccountId="";
                        _self.$message({
                            message: _self.$t('message.global.success'),
                            type: 'success'
                        });                                             
                    },function(err){
                        
                    })  
                }
                
            },
            handleEdit(index, row) {
                this.dlg.dlgFundAccountId = row.id,
                this.dlg.dlgFundAccountName = row.name,
                this.dlg.dlgVisible = true;
                this.dlg.dlgTitle = this.$t('message.fund.account_edit')
            },
            handleDelete(index, row) {
                var _self = this;
                _self.$confirm(_self.$t('message.fund.account_delete_confim'),
                    _self.$t('message.global.prompt'), {
                        confirmButtonText: _self.$t('message.global.confirm'),
                        cancelButtonText: _self.$t('message.global.cancel'),
                        type: 'warning'
                    }).then(() => {
                    _self.winsigns.ds.DELETE({url:_self.winsigns.api.fundURL.fundaccounts+'/'+row.id,
                        data:{}},function(data){
                            _self.getFundAccounts();                 
                            _self.$message({
                                message: _self.$t('message.global.success'),
                                type: 'success'
                            });                                             
                        },function(err){
                            
                        })                   
                    }).catch(() => {
                     _self.$message({
                            type: 'info',
                            message: _self.$t('message.global.cancel_delete')
                        });          
                    });                
            }
        }
    }
</script>
<style scoped>   
    .right_button{
        padding-top:30px;
    }
</style>