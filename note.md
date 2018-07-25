## 簡易手順
プロジェクトをgit cloneする。  
STSから File -> Open projects from file system -> クローン先のディレクトリを選択  
(注意１　pom.xmlのエラーとSpringProject)  

パッケージを右クリックして、Run as -> maven install クリック  
(注意２　プロキシ設定）  
(注意3　テストコード）  
(注意4)

lombokをインストール　ー　lombokのjarをダブルクリックしてSTSを選択する
　lombokを有効にするためにSTSの再起動が必要  

MySqlにテーブルをインポート  
phpMyadmin の場合、画面のインポートからsrc/resource/demo.sqlをインポート  


---
### こけやすいところ
 1. pom.xmlのエラー  
   プロジェクトがSpringとして認識されていない可能性があります。  
	   -> PackageExplorer 上で、プロジェクトに「S」は付いているか。  
	   -> プロジェクトを右クリック -> configureかspringTool -> add Spring Project Nature  

1. プロキシ設定  
   Mavenにプロキシを通す必要があります。  
-> .m2直下にsettings.xml はあるか。（.m2ディレクトリはwindowsだとユーザーの下に大抵はあります）  
  ない場合はsettings.xmlファイルを作成して、以下を記入、hostとportを変更  
  
```
<settings>
  <proxies>
		<proxy>
		  <active>true</active>
		  <protocol>http</protocol>
		  <username>username</username>
		  <password>password</password>
		  <host>***proxyhost***</host>
		  <port>***99***</port>
		  <nonProxyHosts>***hoge***</nonProxyHosts>
	    </proxy>
	</proxies>
</settings>
```
1. テストコード  
    maven install の前にテストコードをコメントアウトしてないとビルドが失敗することがあります。  

1. maven install  
.m2レポジトリにアクセスできない or 見つからない　等のメッセージが出る場合、  
.m2配下のrepositoryディレクトリを削除して再実行すると動く時があります。  

1. jre  
    STSでどのjavaを使うかの設定がされていない場合があります。
MAC  SpringToolSuite -> 環境設定 -> java  
windows  window -> preferences -> java  
(怪しい時は、コンソールから$java --version  $javac --version  を確認)  

1. lombok  
  STSはダウンロードしたjarからlombokをインストールする必要があります。  
コードのgetterやsetterの箇所でエラーになっていたらlombokがインストールされていません。  

1. hibernateのエラー  
    DBのテーブル内容とspringのエンティティクラスが一致していないとアプリが起動しません。  
また、lombokが動いていない場合もhibernateがエラーを出します。(フィールドがない扱いになるから）  




