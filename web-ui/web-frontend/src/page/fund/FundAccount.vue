<template> 
    <div>
        <el-row>        
            <div class="line_bottom">
                <el-button type="text" class="float_right right_button"
                @click="openDialog(row)">添加产品账户</el-button>
                <h1>产品账户</h1>              
            </div>                
            <div class="line_margin_top"></div>            
        </el-row>
        <el-row>        
            <el-col :span="18" :offset="3">                 
                <el-table v-loading="loading" :data="fundAccontList" style="width: 100%" stripe>
                    <el-table-column sortable
                        prop="name"
                        label="名称">
                    </el-table-column>   
                    <el-table-column label="操作">
                        <template scope="scope">
                            <el-button
                            size="small"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)"
                            >删除</el-button>                                                   
                        </template>
                    </el-table-column>                 
                </el-table>
            </el-col>	                     
        </el-row>  

        <el-dialog :title="dlg.dlgTitle" v-model="dlg.dlgVisible" size="tiny">
            <el-form :model="dlg">
                <el-form-item label="产品账户名称" label-width="100px">
                     <el-input v-model="dlg.dlgFundAccountName"></el-input>
                </el-form-item>               
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dlg.dlgVisible=false">取 消</el-button>
                <el-button @click="postFundAccount()">确 定</el-button>
            </div>
        </el-dialog>        
    </div>
</template>
<script>
    import api from '../../config/api.json'
    import ds from '../../common/ds'
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
                ds.GET({url:api.fundURL.funds + '/'+_self.$route.params.fundId+'/fund-accounts',
                        data:{}},function(data){
                    _self.loading = false;
                    _self.fundAccontList = data._embedded?data._embedded["fund-accounts"]:[];                                                                    
                })  
            },
            openDialog: function(){
                this.dlg.dlgTitle = "添加产品账户";
                this.dlg.dlgVisible = true; 
                this.dlg.dlgFundAccountId="";
                this.dlg.dlgFundAccountName="";              
            },
            postFundAccount: function(){
                var _self = this;
                if (_self.dlg.dlgFundAccountId==''){//新增
                    ds.POST({url:api.fundURL.funds + '/'+_self.$route.params.fundId+'/fund-accounts',
                        data:{"name":_self.dlg.dlgFundAccountName}},function(data){
                        _self.getFundAccounts(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundAccountId="";
                        _self.dlg.dlgFundAccountName="";
                        _self.$message({
                            message: '创建产品帐号成功',
                            type: 'success'
                        });                                             
                    },function(err){
                        
                    })  
                } else{//修改
                    ds.PUT({url:api.fundURL.fundaccounts+'/'+_self.dlg.dlgFundAccountId,
                        data:{"name":_self.dlg.dlgFundAccountName}},function(data){
                        _self.getFundAccounts(); 
                        _self.dlg.dlgVisible= false;
                        _self.dlg.dlgFundAccountName="";
                        _self.dlg.dlgFundAccountId="";
                        _self.$message({
                            message: '修改产品帐号成功',
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
                this.dlg.dlgTitle = "编辑产品账户";
            },
            handleDelete(index, row) {
                var _self = this;
                _self.$confirm('您正在删除产品账户 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        ds.DELETE({url:api.fundURL.fundaccounts+'/'+row.id,
                        data:{}},function(data){
                            _self.getFundAccounts();                 
                            _self.$message({
                                message: '删除产品帐号成功',
                                type: 'success'
                            });                                             
                        },function(err){
                            
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
</script>
<style scoped>   
    .right_button{
        padding-top:30px;
    }
</style>