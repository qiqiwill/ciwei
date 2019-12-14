/**
 * Created by liuqi on 2019/12/12.
 */
const userCpn = Vue.component('userCpn',{
    template: `
                <div>
                    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" :key="itemkey" style="width: 100%; overflow-y: auto;">
                            <el-table-column  label="姓名(拼音排序)" width="150">
                                <template slot-scope="scope">
                                  <el-input size="small" v-model="scope.row.name"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column prop="address" label="地址" width="150">
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
                                    <el-button type="primary" icon="el-icon-check" circle @click="saveUser(scope.row)"></el-button>
                                    <el-button type="danger"  icon="el-icon-delete" circle @click="delUser(scope.row.id, (currentPage-1)*pageSize + scope.$index, tableData)"></el-button>
                                </template>
                            </el-table-column>
                    </el-table>
                    <el-pagination
                        style=" padding-top: 20px"
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[10, 20, 30, 50, 100]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="tableData.length">
                    </el-pagination>
                    <el-dialog  :visible.sync="dialogFormVisible">
                      <el-form>
                        <el-form-item label="名字" label-width="70px">
                            <el-input v-model="newname" ></el-input>
                        </el-form-item>
                        <el-form-item label="地址" label-width="70px">
                              <el-select v-model="newcity" value="深圳">
                                <el-option label="深圳" value="深圳"></el-option>
                                <el-option label="上海" value="上海"></el-option>
                                <el-option label="北京" value="北京"></el-option>
                                <el-option label="成都" value="成都"></el-option>
                              </el-select>
                        </el-form-item>
                      </el-form>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="addUser">确 定</el-button>
                      </div>
                    </el-dialog>
                </div>
                   `,
    data(){
        return {
            tableData: [],
            currentPage: 1,
            pageSize: 10,
            dialogFormVisible:false,
            newname:"",
            newcity:"深圳",
            itemkey:"1"
        }
    },
    mounted () {
        axios.get('/admin/user').then(res=>{
            this.tableData = res.data;
        })
    },
    methods:{
        saveUser(row){
            let data = {"id":row.id,"name":row.name, "city":row.city};
            axios.post('/admin/user', data).then(res=>{
                this.$message({
                    message: '修改成功',
                    type: 'success'
                });
            })
        },
        delUser(id, index, rows){
            this.$confirm('确定删除?'+index + "  "+ id, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios.delete('/admin/user/'+id).then(res=>{
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        rows.splice(index, 1);
                    }
                )
            })
        },
        // 每页多少条
        handleSizeChange(val) {
            this.pageSize = val;
        },
        // 当前页
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        //新增用户
        addUser(){
            let currentPage = _this.currentPage;
            this.dialogFormVisible = false
            let data = {"name":this.newname, "city":this.newcity};
            axios.post('/admin/user', data).then(res=>{
                this.$message({
                    message: '新增成功',
                    type: 'success'
                });
                axios.get('/admin/user').then(res=>{
                    this.tableData = res.data;
                })
            })
        }
    }
})