# innovationcamp-week03


## API명세서



![api명세서](https://user-images.githubusercontent.com/91414781/180920875-23e782b9-9370-4a0c-9d83-68725a76f3b1.png)


### 1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)

      수정과삭제일때 request param에 id값과 request body에 password를 서버에 보내주면 
      서버에서 id와password가 올바른지 확인하고 수정할수있게한다.

### 2. 어떤 상황에 어떤 방식의 request를 써야하나요?
      
      포스팅을 등록할때 Post Method로 request body에 Json형태로 title,content,password,author를 작성해서 서버로 보내준다
      전체 포스팅을 볼때는 Get Method로 요청 특정 id의 포스팅을 볼때는 request param에 id값을 넣어줘서 요청
      올바른 비밀번호 인지 확인할때는 Post Method로 request param에 id값,request body에 패스워드를 넣어주어서 요청


### 3. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
    Controller에서 요청/응답을 처리하고
    Service에서 받아온 요청을 update한다
    Repository부분은 DB를 연결및 CRUD작업


