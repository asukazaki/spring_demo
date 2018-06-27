# spring_demo

勉強会用のSpringAPIデモプロジェクト  

---
## 概要
GETとPOSTを受け取って、DBを操作するサンプルになっています。  
認証、ロガー、例外ハンドリングは実装されていません。

---
## 構成
├── README.md	                    これ  
├── pom.xml		                     依存関係記述  
├── src  
│   ├── main  
│   │   ├── java  
│   │   │   └── demo  
│   │   │       ├── DemoApplication.java      SpringBoot起動  
│   │   │       ├── app							Contoroller層  
│   │   │       │   ├── JobsController.java  
│   │   │       │   └── UsersController.java  
│   │   │       ├── bean							json受け取りクラス（要検討）  
│   │   │       │   └── JobInputBean.java  
│   │   │       ├── entity						O/Rマッパーのオブジェクト  
│   │   │       │   ├── Job.java  
│   │   │       │   ├── JobPk.java				複合キー  
│   │   │       │   └── User.java  
│   │   │       ├── repository					DBアクセッサ  
│   │   │       │   ├── JobsRepository.java  
│   │   │       │   └── UserRepository.java  
│   │   │       └── service						サービス層  
│   │   │           └── JobService.java  
│   │   └── resources  
│   │       └── application.properties			設定ファイル  
   └── test  
       └── java  
           └── demo  
               └── UserControllerTest.java  

---
## 事前準備
maven install  
MySql にresoucesからimport  
STSにlombokをインストール  
STSの環境設定で補完機能アップ  

---
## 課題
とりあえず動かしてみましょう！！

POSTMANでPOSTするとエラーになったので
POSTする時は、コンソールから以下を叩いてみてください。

```
curl -X POST localhost:8099/demo/jobs/1/2018/06/27/ -H 'content-type: application/json;charset=UTF-8' -d '{"startTime":"09:00:00","jobStateCode":"1","endTime":"19:00:00","restStartTime":"","restEndTime":"","jobStateName":""}'
```

#### 課題１
user テーブルのUPDATEとDELETEを実装してください。  
・ITItestの名前を変更してください  
・ITItestレコードを削除してください  
+@  
JunitとHttpClientを用いてその結果をテストコードから分かるようにしてください  
（Jacksonマッパーが使えます!!）  

#### 課題２  
残業時間の計算がこのままではできません。  
仮に、祝日テーブルが６月に存在したとして、  
どうすれば、残業時間が計算できるか、  
レスポンスを返すためにどのようなクラスを用意すればいいか  
(入れ子構造でクラスを作るか、そのまま返すか）  
設計を考えてください。  

#### 課題３  
例外ハンドリングを実装してください  
springにはSecurityや例外ハンドラーが多く用意されています。  
今回どの程度やればいいかを考えてみてください。
