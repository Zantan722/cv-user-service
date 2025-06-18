# CV User Service
Java 使用版本為 openjdk:20

Spring Boot 版本為 3.2.0

## 事前準備
1. 確認已安裝 Docker
2. 確認已安裝 Maven
3. 確認已安裝 JAVA SDK 20

## 部署程式
### 方法一 (不使用docker)
建置服務，將Repository抓下後，到該repo的根目錄執行
```bash
maven clean package
```
```bash
java -jar ./target/cv-user-service-1.0-SNAPSHOT.jar
```

### 方法二 (使用docker)
建置服務，將Repository抓下後，到該repo的根目錄執行
```bash
docker build -t cv-user-service:latest .
```

此時需注意，是否已產生前端的image檔案`cv-blog-nginx:latest`，若沒有，請先到前端的repo執行方法二
網址為`https://github.com/Zantan722/cv-blog-front-end/tree/main`
產生完前後端的image後，執行以下指令，即可正常啟動服務
```bash
docker-compose up
```

### 方法三
請直接到 https://cv-front-end-683332902245.asia-east1.run.app 進行使用

## 測試帳號
### ADMIN帳號

帳號：admin@gmail.com

密碼：Aaa123123123


### USER帳號

帳號：test@gmail.com

密碼：Aaa123123123
## SWAGGER路徑
http://localhost:8080/swagger-ui/index.html

https://cv-front-end-683332902245.asia-east1.run.app/swagger-ui/index.html

## 補充內容
### 設計理念與使用
1. 主要使用Spring Boot 進行開發，並使用 Spring Security 進行權限控管
2. 使用 JWT 進行登入驗證
3. 使用 MySQL 進行資料庫存取
4. Url Path 使用commom, user, admin 進行區分,
   - common: 不需要登入即可存取的API
   - user: 需要登入後才能存取的API
   - admin: 需要ADMIN角色才能存取的API 
5. 使用 Swagger 進行 API 文件生成
6. 使用 JPA 進行資料庫存取 
7. 使用 Spring Boot Test 進行單元測試 
8. 使用 Spring Boot Validation 進行參數驗證
9. 使用controller, service, repository 分層架構進行開發
10. 使用timestamp進行時間的處理，避免時區問題
11. 針對API的處理
    - 針對API的處理，使用了全域異常處理，只要是預期性的錯誤，
      都會回傳統一的錯誤格式，並且不會暴露系統內部的錯誤訊息
    - 非預期性的錯誤，則會回傳500錯誤碼，使前端可以進行處理，不將內部錯誤進行拋出
    - 針對API的回傳格式，統一使用`Result`進行回傳，
      其中包含`code`, `message`, `data`三個欄位，分別代表狀態碼、訊息、回傳資料，只要status不為0即為錯誤
    - 針對API分頁式的回傳資料，使用`Page`進行分頁處理，並且使用`List`進行資料的回傳
12. 使用liquidbase進行資料庫的版本控制，並且使用`changelog.xml`進行資料庫的版本控制
13. db並沒有使用foreign key進行table之間的控管，因為在過往經驗中，使用foreign key在資料量大量異動時會造成資料庫的效能下降，且在資料庫的操作上會有一些限制，因此在這個專案中並沒有使用foreign key進行table之間的控管，而是使用程式碼進行控管
14. BLOG的查詢有增加order by與sort功能


### 覺得可以在優化的地方
1. 因為JWT的特性，會有token過期的問題，可以再增加refresh token的功能，讓使用者可以在token過期後，重新取得新的token
2. Blog資料表裡面的tags欄位，使用了`json`來存放tags的資料，若之後有需要依照tags進行找碴資料，可以考慮使用多對多的關聯來存放tags的資料
3. Exception處理可以再增加更多的錯誤處理，不是只有用-1,0來表示，可以整合更多的錯誤碼，讓前端可以更清楚的知道錯誤的原因


### 加分項目
1. 實作 JWT 驗證機制
2. 整合 Spring Security 
3. 自動化測試覆蓋率報表
    - 測試覆蓋率報表在/htmlReport/index.html，該報表為使用頁面上點擊個功能產生的，並非寫JUnit產生
    - 有使用mock進行實作Junit的code
4. 資料庫 schema migration（Liquibase）