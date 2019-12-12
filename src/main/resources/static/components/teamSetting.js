/**
 * Created by liuqi on 2019/12/12.
 */

const teamSettingCpn = Vue.component('teamSettingCpn',{
    template: `
        <div>
            <el-table :data="tableData"  style="width: 100%; overflow-y: auto;">
                            <el-table-column  label="分组数量" width="150">
                                <template slot-scope="scope">
                                  <el-input size="small" v-model="scope.row.teamNum" type="number"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column  label="每组人数" width="150">
                                <template slot-scope="scope">
                                  <el-input size="small" v-model="scope.row.teamPersonNum"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column label="地址" width="150">
                                <template slot-scope="scope">
                                   <el-select v-model="scope.row.city" placeholder="请选择">
                                    <el-option label="深圳" value="深圳"></el-option>
                                    <el-option label="上海" value="上海"></el-option>
                                    <el-option label="北京" value="北京"></el-option>
                                    <el-option label="成都" value="成都"></el-option>
                                  </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column  label="操作">
                                  <template slot="header" slot-scope="scope">
                                    <div>
                                        <span>操作</span>
                                        <el-button type="success" @click="dialogFormVisible = true">新增</el-button>
                                    </div>
                                  </template>
                                <template slot-scope="scope">
                                    <el-button type="primary" icon="el-icon-check" circle @click="saveSetting(scope.row)"></el-button>
                                    <el-button type="danger"  icon="el-icon-delete" circle @click="delSetting(scope.row.id, scope.$index, tableData)"></el-button>
                                </template>
                            </el-table-column>
            </el-table>
             <el-dialog  :visible.sync="dialogFormVisible">
                      <el-form>
                        <el-form-item label="分组数量" label-width="70px">
                            <el-input v-model="newTeamNum" ></el-input>
                        </el-form-item>
                        <el-form-item label="每组人数" label-width="70px">
                            <el-input v-model="newTeamPersonNum" ></el-input>
                        </el-form-item>
                        <el-form-item label="地址" label-width="70px">
                              <el-select v-model="newCity" value="深圳">
                                <el-option label="深圳" value="深圳"></el-option>
                                <el-option label="上海" value="上海"></el-option>
                                <el-option label="北京" value="北京"></el-option>
                                <el-option label="成都" value="成都"></el-option>
                              </el-select>
                        </el-form-item>
                      </el-form>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="addSetting">确 定</el-button>
                      </div>
                    </el-dialog>
        </div>
    `,
    data(){
        return {
            tableData: [],
            dialogFormVisible:false,
            newTeamNum: 0,
            newTeamPersonNum:0,
            newCity:"深圳"
        }
    },
    mounted () {
        axios.get('/admin/teamSetting').then(res=>{
            this.tableData = res.data;
        })
    },
    methods:{
        saveSetting(row){
            let data = {"id":row.id, "teamNum":row.teamNum, "teamPersonNum": row.teamPersonNum,"city":row.city};
            axios.post('/admin/teamSetting', data).then(res=>{
                if(res.data.code != '000'){
                    this.$message({
                        message: res.data.msg,
                        type: 'error'
                    });
                }else{
                    this.$message({
                        message: '修改成功',
                        type: 'success'
                    });
                }
            })
        },
        delSetting(id, index, rows){
            this.$confirm('确定删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios.delete('/admin/teamSetting/'+id).then(res=>{
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        rows.splice(index, 1);
                    }
                )
            })
        },
        //新增
        addSetting(){
            this.dialogFormVisible = false
            let data = {"teamNum":this.newTeamNum,"teamPersonNum": this.newTeamPersonNum, "city":this.newCity};
            axios.post('/admin/teamSetting', data).then(res=>{
                if(res.data.code != '000'){
                    this.$message({
                        message: res.data.msg,
                        type: 'error'
                    });
                }else{
                    this.$message({
                        message: '新增成功',
                        type: 'success'
                    });
                    axios.get('/admin/teamSetting').then(res=>{
                        this.tableData = res.data;
                    })
                }
            })
        }
    }

})