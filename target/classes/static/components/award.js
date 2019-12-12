/**
 * Created by liuqi on 2019/12/12.
 */

const awardCpn = Vue.component('awardCpn',{

    template: `
        <div>
            <el-table :data="tableData"  style="width: 100%; overflow-y: auto;">
                            <el-table-column  label="分组数量" width="150">
                                <template slot-scope="scope">
                                  <el-input size="small" v-model="scope.row.name"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column  label="每组数量" width="150">
                                <template slot-scope="scope">
                                  <el-input size="small" v-model="scope.row.name"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column prop="address" label="地址" width="150">
                                <template slot-scope="scope">
                                   <el-select v-model="scope.row.city" placeholder="请选择">
                                    <el-option label="深圳" value="深圳"></el-option>
                                    <el-option label="上海" value="上海"></el-option>
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
        </div>
    `,

})