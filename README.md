# ShoppingClothes Android购物应用介绍文档

## 项目概述

ShoppingClothes是一款基于Android平台的服装购物应用，采用客户端-服务端架构设计。应用提供了完整的购物流程，包括用户注册登录、商品浏览、购物车管理、订单处理等功能。

## 技术架构

### 1. 整体架构
- **客户端**: Android (API Level 14, Android 4.0+)
- **服务端**: Java Web应用 
- **数据库**: SQL Server
- **通信协议**: HTTP + JSON

### 2. 项目结构

```
ShoppingClothes/                 # Android客户端
├── src/                        # 源代码
│   ├── background/focus/       # 主界面和焦点图相关
│   ├── shopping/clothes/       # 登录注册相关
│   ├── search/clothes/         # 商品搜索相关
│   ├── mianinfo/information/   # 个人信息和购物车
│   └── Values/bean/           # 数据模型和常量
├── res/                       # 资源文件
│   ├── layout/               # 布局文件
│   ├── drawable-*/           # 图片资源
│   └── values/              # 字符串和样式
└── AndroidManifest.xml       # 应用配置清单

ShoppingServer/                 # Java Web服务端
├── src/                       # 服务端源代码
│   ├── shopping/             # 登录注册服务
│   ├── infomation/          # 信息服务
│   └── zJSPServlet/         # JSP相关服务
└── WebContent/              # Web内容
    ├── WEB-INF/web.xml      # Web应用配置
    └── *.jsp               # JSP页面
```

## 核心Android技术

### 1. Activity生命周期管理

**主要Activity类:**
- `MainActivity`: 应用主界面，实现侧滑菜单效果
- `ShoppingLogin`: 用户登录界面
- `MainFocusActivity`: 焦点图展示界面
- `info_cart`: 购物车管理界面

**技术特点:**
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
    setContentView(R.layout.activity_main);
}
```

### 2. 自定义UI组件

#### 2.1 侧滑菜单实现
- **技术要点**: 自定义触摸事件处理，实现流畅的侧滑效果
- **核心类**: `MainActivity` 实现 `OnTouchListener`
- **关键技术**:
  - `VelocityTracker`: 手势速度检测
  - `AsyncTask`: 平滑滚动动画
  - 自定义布局参数动态调整

```java
public boolean onTouch(View v, MotionEvent event) {
    createVelocityTracker(event);
    switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            xDown = event.getRawX();
            break;
        case MotionEvent.ACTION_MOVE:
            xMove = event.getRawX();
            // 动态调整菜单位置
            break;
        case MotionEvent.ACTION_UP:
            // 根据滑动距离和速度决定菜单状态
            break;
    }
    return true;
}
```

#### 2.2 焦点图轮播
- **核心组件**: 自定义`FocusGallery`继承自`Gallery`
- **适配器**: `FocusAdapter`继承`BaseAdapter`
- **技术实现**:
  - 图片资源动态加载
  - 指示器状态同步
  - 触摸事件响应

```java
gallery.setOnItemSelectedListener(new OnItemSelectedListener(){
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, 
                              int position, long arg3) {
        switch(position){
            case 0:
                focusPointImage.setBackgroundResource(R.drawable.focus_point_1);
                break;
            // 其他位置处理...
        }
    }
});
```

### 3. 网络通信技术

#### 3.1 HTTP客户端实现
- **核心技术**: Apache HttpClient
- **数据格式**: 
  - 请求: URL编码表单数据
  - 响应: UTF-8编码字符串或对象序列化

```java
// POST请求实现
HttpPost httpPost = new HttpPost(URL);
List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("username", user));
params.add(new BasicNameValuePair("password", pass));

httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
HttpResponse httpResponse = client.execute(httpPost);
```

#### 3.2 异步网络请求
- **技术选择**: `AsyncTask`
- **应用场景**: 登录验证、数据获取、购物车操作
- **错误处理**: 网络异常捕获和用户提示

```java
class AT extends AsyncTask<Object, Object, Object> {
    @Override
    protected Object doInBackground(Object... params) {
        // 网络请求处理
        try {
            HttpResponse response = client.execute(httpPost);
            // 处理响应数据
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    @Override
    protected void onPostExecute(Object result) {
        // UI更新
        if("success".equals(result)) {
            // 跳转到主界面
            Intent intent = new Intent(ShoppingLogin.this, MainFocusActivity.class);
            startActivity(intent);
        }
    }
}
```

### 4. 数据持久化

#### 4.1 SharedPreferences应用
- **用途**: 用户登录信息记忆
- **实现**: "记住密码"功能

```java
// 保存用户信息
SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
SharedPreferences.Editor edit = remdname.edit();
edit.putString("name", username.getText().toString());
edit.putString("pass", password.getText().toString());
edit.commit();

// 读取用户信息
String name_str = remdname.getString("name", "");
String pass_str = remdname.getString("pass", "");
```

### 5. 列表视图技术

#### 5.1 ListView + SimpleAdapter
- **应用场景**: 商品列表展示、购物车列表
- **数据绑定**: Map数据结构到视图组件

```java
simpleAdapter = new SimpleAdapter(this, 
    list, 
    R.layout.search_shirt,
    new String[]{"shirt_url","shirt_name","shirt_price"}, 
    new int[]{R.id.shirt_url,R.id.shirt_name,R.id.shirt_price});
lv.setAdapter(simpleAdapter);
```

#### 5.2 自定义适配器
- **继承关系**: `BaseAdapter`
- **视图复用**: `convertView`机制
- **数据更新**: `notifyDataSetChanged()`

### 6. 事件处理机制

#### 6.1 触摸事件处理
- **接口实现**: `OnTouchListener`
- **事件类型**: `ACTION_DOWN`, `ACTION_MOVE`, `ACTION_UP`
- **应用场景**: 按钮按压效果、侧滑菜单

```java
button.setOnTouchListener(new OnTouchListener() {
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            // 按下效果
            button.setBackgroundResource(R.drawable.button_pressed);
        } else if(event.getAction() == MotionEvent.ACTION_UP){
            // 释放效果
            button.setBackgroundResource(R.drawable.button_normal);
        }
        return false;
    }
});
```

#### 6.2 点击事件处理
- **接口实现**: `OnClickListener`
- **应用场景**: 按钮点击、列表项选择

## 服务端技术

### 1. Servlet技术栈
- **核心框架**: Java Servlet API
- **服务器**: 支持Servlet容器 (如Tomcat)
- **数据库连接**: JDBC + SQL Server

### 2. 主要Servlet服务

#### 2.1 用户认证服务
- **LoginServlet**: 用户登录验证
- **RegistServlet**: 用户注册
- **数据访问**: `UserDAO`类封装数据库操作

#### 2.2 商品服务
- **shirt_search**: 商品搜索
- **search_clothes**: 服装分类搜索
- **search_chilren**: 儿童服装搜索

#### 2.3 购物车服务
- **shopping_car**: 添加商品到购物车
- **shopping_catSearch**: 获取购物车内容
- **shopping_changeCart**: 修改购物车
- **shopping_readOrder**: 订单查询

## 应用功能模块

### 1. 用户管理模块
- **登录功能**: 用户名密码验证
- **注册功能**: 新用户注册
- **记住密码**: 本地存储用户凭据
- **个人信息**: 用户信息查看和编辑

### 2. 商品浏览模块
- **首页轮播**: 焦点图展示
- **分类浏览**: 服装、衬衫、儿童装分类
- **商品搜索**: 关键词搜索功能
- **商品详情**: 商品信息展示

### 3. 购物车模块
- **添加商品**: 商品加入购物车
- **数量调整**: 购买数量修改
- **商品删除**: 移除购物车商品
- **价格计算**: 自动计算总价
- **批量操作**: 批量选择和删除

### 4. 订单管理模块
- **订单生成**: 购物车结算生成订单
- **订单查询**: 历史订单查看
- **订单状态**: 订单状态跟踪

## 技术总结

项目展示了以下核心Android开发技术：

1. **Activity生命周期管理**和**Intent导航**
2. **自定义UI组件**和**触摸事件处理**
3. **HTTP网络通信**和**异步任务处理**
4. **数据持久化**技术
5. **ListView适配器模式**和**数据绑定**
6. **资源管理**和**多屏幕适配**
7. **客户端-服务端架构**设计

以上就是整个服装商城的设计文档介绍。 
