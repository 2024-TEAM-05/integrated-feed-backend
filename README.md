
# 소셜 미디어 통합 Feed 서비스

![인스타그램](https://img.shields.io/badge/Instagram-%23E4405F.svg?style=for-the-badge&logo=Instagram&logoColor=white) ![쓰레드](https://img.shields.io/badge/Threads-000000?style=for-the-badge&logo=Threads&logoColor=white) ![페이스북](https://img.shields.io/badge/Facebook-%231877F2.svg?style=for-the-badge&logo=Facebook&logoColor=white) ![트위터](https://img.shields.io/badge/X-%23000000.svg?style=for-the-badge&logo=X&logoColor=white) 등 다양한 SNS에 게시된 컨텐츠를 한 곳에 모아 보여주는 서비스


##  목차
- [✨ 개요](#-개요)
- [Skils](#Skills)
- [Installation](#Installation)
- [📄 요구사항 정리 및 기술 명세서](#-요구사항-정리-및-기술-명세서)
- [🏗️ ERD](#%EF%B8%8F-erd)
- [🕝 프로젝트 진행 및 이슈 관리](#-프로젝트-진행-및-이슈-관리)
- [👾 Authors](#-authors)


</br>

## ✨ 개요

다양한 소셜 미디어 플랫폼을 통해 수많은 정보를 접할 수 있게 되었지만, 여러 SNS 상에서 산발적으로 게시되는 컨텐츠를 일일이 확인하고 관리하는 건 굉장히 번거로운 일입니다. 어떤 주제나 브랜드에 대해 다양한 사용자 입장에서 모니터링하려면 각 플랫폼을 따로 관리해야 하는 불편함이 있습니다. 이런 문제를 해결할 수 있는 소셜 미디어 통합 Feed 애플리케이션입니다. 

`소셜 미디어 통합 Feed 서비스`는 `유저 계정` 또는 브랜드의 `#해시태그`를 기반으로 인스타그램, 쓰레드, 페이스북, 트위터(X) 등 다양한 SNS에 게시된 관련 컨텐츠를 하나의 피드로 통합하여 제공합니다. 이를 통해 관심 있는 주제나 브랜드의 SNS 노출 현황과 통계를 한눈에 쉽게 확인할 수 있습니다.

<br/>

## 📄 요구사항 정리 및 기술 명세서

<details>
	<summary> 로그인 API</summary>
	
### **요약 (Summary)**

유저는 가입한 계정, 비밀번호로 로그인합니다. 서비스 로그인 시 메뉴는 **통합 Feed** 단일 입니다. 

### **목표 (Goals)**

- `계정`, `비밀번호` 로 로그인 시 `JWT` 가 발급됩니다.
- **이후 모든 API 요청 Header 에 `JWT` 가 항시 포함되며, `JWT` 유효성을 검증합니다.**
- 서비스 로그인 시, 메뉴는 **통합 Feed** 단일 입니다. 

### **목표가 아닌 것 (Non-Goals)**

- JWT 발급받은 후 유저를 통합 Feed 페이지로 프론트엔드 리디렉션 처리
- 계정 잠금, 자동 로그아웃 등 사용자 경험 관련 추가 로직

### 로그인 api 요구사항 상세
- **설명**
    - 사용자는 계정과 비밀번호를 입력하여 로그인할 수 있으며, 로그인 성공 시 JWT 토큰을 발급받습니다. (**Authentication)**
    - 이후 모든 API에서 JWT 토큰을 사용하여 인가 요청을 보낼 수 있습니다. **(Authorization)**
- **입력 데이터**
    - `계정(아이디)`
    - `비밀번호`
- **출력 데이터**
    - `JWT 토큰`
- **처리 과정**:
    1. 사용자가 입력한 계정과 비밀번호를 검증합니다.
    2. 검증이 성공하면 JWT 토큰을 생성합니다.
    3. 생성된 JWT 토큰을 사용자에게 반환합니다.
    4. 사용자는 이후의 API 요청 시 이 JWT 토큰을 헤더에 포함하여 서버에 요청을 보냅니다.
    5. 서버는 각 요청에서 JWT 토큰의 유효성을 검증하여 인가 처리합니다.
- **예외 사항**
    - 입력한 계정(아이디)이나 비밀번호가 잘못된 경우, 오류 메시지와 함께 인증 실패 응답을 반환합니다.
    - 만료된 JWT 토큰으로 요청한 경우, 인증 오류 응답을 반환합니다.
    - JWT 토큰이 없는 경우, 인증되지 않은 상태로 처리하여 오류 응답을 반환합니다.
  
 </br>
 
<details>
	<summary> 플로우 차트 </summary>

```mermaid
flowchart TD
    A[유저] -->|로그인 요청| B[로그인 API]
    B -->|검증| C[(사용자 DB)]
    
    C -->|유효한 사용자| D{JWT 생성 및 반환}
    C -->|유효하지 않은 사용자| E[Error Message]

    D --> F[API 요청]

    subgraph "🔒 Authorization"
        F --> G[보호된 API]
        G --> H{JWT 유효성 검증}
        H -->|유효| J[응답]
        H -->|유효하지 않음| K[Unauthorized error]
    end
```
</details>

<details>
	<summary> 유저 ERD </summary>

```mermaid
erDiagram
member {
	member_id BIGINT PK "BIGSERIAL"
	account VARCHAR(50) UK "NOT NULL"
	email VARCHAR(320) UK "NOT NULL"
	password VARCHAR(255) "NOT NULL"
	status VARCHAR(15) "NOT NULL DEFAULT UNVERIFIED"
  created_at TIMESTAMP "NOT NULL DEFAULT CURRENT_TIMESTAMP"
	updated_at TIMESTAMP "NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
}
```
</details>

### **계획 (Plan)**

- DTO, 서비스, 레파지토리 구현
    - 계정(아이디) 및 비밀번호를 사용한 유저 인증 로직 작성
- JWT 토큰 생성 로직 구현
- **JWT 검증 로직 구현**
    - JWT 검증 필터 설정
        - Spring Security 설정
        - JWT 인증 필터 작성 - 로그인, 회원가입 및 인증 제외 모든 요청이 필터를 통과하도록
    - 기본적인 예외 처리 로직 구현 (유효하지 않은 JWT, 만료된 JWT 등)
- 로그인 컨트롤러 구현
- 테스트 코드 작성 및 기능 테스트
    - JWT, 로그인 API, Spring Security 설정 유닛 테스트
    - 통합 테스트

### **이외 추가 고려 사항들 (Other Considerations)**

- JWT 만료 시간, 비밀키 저장 방법, 만료 전 갱신 등
- 로그인 시도, 실패, 성공, 토큰 만료 등의 이벤트 로깅
- 잘못된 로그인 정보, 만료된 토큰, 권한 부족 등 에러 처리

### **마일스톤 (Milestones)**
![image](https://github.com/user-attachments/assets/dbd228fd-2e98-4f61-aff3-bb2bde808265)

 
</details>

<details>
	<summary> 게시물 상세 조회 API</summary>

### **요약 (Summary)**

게시물 상세 조회 API는 사용자에게 특정 게시물의 상세 정보를 제공하는 기능입니다. 사용자는 이 API를 통해 게시물의 제목, 내용 및 게시물 관련 메타데이터를 조회할 수 있습니다.

### **목표 (Goals)**

- 사용자가 특정 게시물의 모든 세부 정보를 한 번의 API 호출로 가져올 수 있도록 한다.
- 사용자가 요청한 게시물이 존재하지 않는 경우, 적절한 에러 메시지를 반환한다.
- 특정 게시물의 상세 정보 반환에 성공할 경우, 해당 게시물의 view_count를 1 증가시킨다.

### **계획 (Plan)**

<details>
	<summary> 플로우 차트</summary>

```mermaid 
graph TD
    A((게시물 id)) --> B{유효성 검증}

    B -->|실패| C((400 Bad Request 반환))
    
    B -->|성공| D{게시물 존재 확인}
    
    D -->|부재| E((404 Not Found 반환))
    
    D -->|존재| H((200 성공 응답 반환))
```

</details>

<details>
	<summary> 시퀀스 다이어그램 </summary>

```mermaid
sequenceDiagram
    participant C as Client
    participant Co as Controller
    participant S as Service
    participant R as Repository
    
    C->>Co: Request (GET /api/posts/{id})
    Co->>S: getPostDetails(id)
    S->>R: findDetailedPostById(id)
    R-->>S: Post
    S-->>Co: PostDto
    Co-->>C: Response (JSON)
```

</details>

### **유닛 테스트**

- [성공] 게시물 상세 정보가 정상적으로 반환된다.
- [실패] 게시물이 없을 때 예외를 던진다.
- [성공] 조회된 게시물의 view_count가 1 증가한다.
- [실패] 잘못된 형식의 ID가 제공되면 400 Bad Request가 반환된다.

### **마일스톤 (Milestones)**

> 8월 21일 (수): 요구사항 분석 <br>
> 8월 22일 (목): 테크 스펙 작성 <br>
> 8월 23일 (금): 기능 구현 및 단위 테스트 작성 <br>
> 8월 25일 (일): 테스트 수정 및 리드미 작성 <br>

</details>


<details>
	<summary> 게시물 “좋아요수”, “공유수” 증가 API</summary>

### **요약 (Summary)**

해당 서비스에서 `좋아요`, `공유` 버튼을 클릭할 시 각 게시물의 원래 소셜 미디어의 `좋아요` , `공유` 수를 증가시킬 수 있게 합니다.

### **목표 (Goals)**

- infra 계층에 각 외부서비스들의 클라이언트를 만듭니다.
- 본 서비스의 `좋아요`, `공유` 수를 외부 서비스에 반영시킵니다.
- 각 게시물의 `소셜미디어`에 맞게 외부서비스에서  `좋아요` 및 `공유`를 늘리는 API 엔드포인트를 구현합니다.
- 실제 API 호출을 시뮬레이션하여 외부 API와의 상호 작용을 추상화함으로써 실제 환경으로 전환할 때 변경 사항의 영향을 줄입니다.
- `event` 를 활용하여 타사 API 상호 작용 시뮬레이션을 비동기식으로 처리하여 서비스 내의 '좋아요' 및 '공유'에 대한 업데이트가 기본 애플리케이션 흐름을 차단하지 않고 효율적으로 처리되도록 합니다.

### **목표가 아닌 것 (Non-Goals)**

- 실제 외부 서비스의 API를 직접 호출하는 기능 개발
- 처리 중에 실제 외부 서비스의 응답을 동기적으로 기다리는 기능 개발
- 살제 외부 서비스의 응답을 기반으로 내부 서비스 로직을 처리하는 기능
- `좋아요` 수와 `공유` 수를 저장했다가 한번에 반영하는 것

### **계획 (Plan)**

<details>
	<summary> 플로우 차트</summary>

```mermaid
graph TD
   A((게시물 좋아요, 공유 요청)) --> BB{JWT 토큰 확인}
   BB --> |유효| B{postId 존재 여부 확인}
   BB --> |유효하지 않음| GG((401 인증 실패 반환)) 
   B --> |존재| E{비동기로 내부 db 종아요, 공유 수 증가 호출}
   E --> |증가 성공| U[내부 db 좋아요, 공유 수 1 증가]
   E --> |증가 실패| KK((500 서버 오류 반환))
   B --> |존재| D[비동기로 좋아요, 공유 수 증가 이벤트 발송]
   D --> F{외부 API 호출 시뮬레이션}
   F -->  UU((요청 로그 기록))
   B --> |존재하지 않음| PP((400 잘못된 요청 반환))

```

</details>

<details>
	<summary> 클래스 다이어그램 </summary>

```mermaid
classDiagram
    class InstagramAdapter {
        +InstagramApi instagramApi
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class TwitterAdapter {
        +TwitterApi twitterApi
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class FacebookAdapter {
        +FacebookApi facebookApi
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class ThreadsAdapter {
        +ThreadsApi threadsApi
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class InstagramApi {
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class TwitterApi {
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class FacebookApi {
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class ThreadsApi {
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class PostService {
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    class PostController {
        +void increaseLikeCount(Long postId)
        +void increaseShareCount(Long postId)
    }

    PostService --> InstagramAdapter
    PostService --> TwitterAdapter
    PostService --> FacebookAdapter
    PostService --> ThreadsAdapter
    InstagramAdapter --> InstagramApi
    TwitterAdapter --> TwitterApi
    FacebookAdapter --> FacebookApi
    ThreadsAdapter --> ThreadsApi
    PostController --> PostService

```

</details>

<details>
	<summary> 시퀀스 다이어그램 </summary>

```mermaid
sequenceDiagram
    participant Client
    participant PostController
    participant PostService
    participant InstagramAdapter
    participant TwitterAdapter
    participant FacebookAdapter
    participant ThreadsAdapter
    participant InstagramApi
    participant TwitterApi
    participant FacebookApi
    participant ThreadsApi

    Client ->> PostController: POST /posts/{postId}/like
    PostController ->> PostService: increaseLikeCount(postId)
    
    alt Instagram Post
        PostService ->> InstagramAdapter: increaseLikeCount(postId)
        InstagramAdapter ->> InstagramApi: increaseLikeCount(Long postId)
    end

    alt Twitter Post
        PostService ->> TwitterAdapter: increaseLikeCount(postId)
        TwitterAdapter ->> TwitterApi: increaseLikeCount(Long postId)
    end

    alt Facebook Post
        PostService ->> FacebookAdapter: increaseLikeCount(postId)
        FacebookAdapter ->> FacebookApi: increaseLikeCount(Long postId)
    end

    alt Threads Post
        PostService ->> ThreadsAdapter: increaseLikeCount(postId)
        ThreadsAdapter ->> ThreadsApi: increaseLikeCount(Long postId)
    end

    InstagramApi -->> InstagramAdapter: Acknowledgment
    TwitterApi -->> TwitterAdapter: Acknowledgment
    FacebookApi -->> FacebookAdapter: Acknowledgment
    ThreadsApi -->> ThreadsAdapter: Acknowledgment

    InstagramAdapter -->> PostService: Acknowledgment
    TwitterAdapter -->> PostService: Acknowledgment
    FacebookAdapter -->> PostService: Acknowledgment
    ThreadsAdapter -->> PostService: Acknowledgment

    PostService -->> PostController: Acknowledgment
    PostController -->> Client: Acknowledgment

```

</details>

### **마일스톤 (Milestones)**

> `~ 8.22(목)`: controller 계층 구현
> 
> `~ 8.23(금)`: infra 계층의 adapter,client 구현
> 
> `~ 8.24(토)`: service 구현, 단위 테스트 작성
> 
> `~ 8.25(일)`: Rollout

</details>


<details>
	<summary> 게시물 목록 API</summary>
        
### **요약 (Summary)**

사용자가 Feed에서 볼 수 있는 게시물 목록을 조회하는 기능을 제공합니다. 다양한 쿼리 파라미터를 지원하여 해시태그, 게시물 유형, 정렬 순서, 검색 키워드 등 사용자가 원하는 방식으로 게시물을 필터링하고 정렬할 수 있게 합니다.

### **목표 (Goals)**

- 유저가 자신의 `account`가 해시태그로 포함된 게시물 목록을 확인할 수 있게 한다.
- 유저가 지정한 한 개 `hashtag` 가 게시물의 해시태그 중 한 개와정확히 일치하는 게시물 목록을 확인할 수 있게 한다.
- 유저가 지정한 소셜 미디어 플랫폼의 게시물 목록을 확인할 수 있게 한다.
- 유저가 오름차순 및 내림차순을 모두 지원하여 `좋아요개수`, `공유 개수`, `조회수`, `생성 날짜`, `업데이트 날짜`와 같은 여러 필드를 기준으로 `오름차순`, `내림차순`으로 정렬된 게시물 목록을 확인할 수 있게 한다.
- 유저가 게시물의 제목과 내용을 검색하여 포함된 게시물 목록을 확인할 수 있게 한다.
  - 내용은 최대 20자까지만 포함되게 한다.

### **목표가 아닌 것 (Non-Goals)**

- 여러 개의 `hashtag` 가 포함된 게시물을 목록을 확인 기능 개발
- 여러 정렬 기준을 동시에 지원하는 기능 개발
- 특정 `hashtag`를 제외한 게시물 목록을 검색하는 기능 개발
- `hashtag`를 포함하거나 유사하지만 정확히 일치하지 않는 게시물을 검색하는 기능 개발

### **계획 (Plan)**

#### 페이지 네이션

- 공통으로 요청을 받을 수 있는 `PaginationQuery` 생성
- 공통으로 응답을 받을 수 있는 `PaginationMetadata` 생성
  - 응답 예시
  ```json
  {
    "hasNext": true,
    "total": 100
  }
  ```

#### QueryDSl을 사용한 조회

- 동적 쿼리 생성을 위해 QueryDSL 사용

#### 테스트

- 단위 테스트 작성
- 리포지토리 테스트 작성

<details>
	<summary> 플로우 차트</summary>

```mermaid
graph TD
    A((게시물 목록 조회 요청)) --> BB{jwt 확인}
    BB --> |존재| B{hashtag \n search \n orderBy \n page_count \n page \n \n 존재}
    BB --> |존재하지 않음| GG((401 오류 응답 반환))
    B --> |존재| C[게시물 목록 조회 쿼리에 요청 값 포함]
    B --> |존재하지 않음| G[기본깂이 존재하면 포함, 존재하지 않으면 불포함]
	  C --> dd[게시물 목록 반환]
	  G --> dd[게시물 목록 반환]
	  dd --> df((성공응답 반환))

```

</details>

<details>
	<summary> 시퀀스 다이어그램 </summary>

```mermaid
sequenceDiagram
    participant 사용자
    participant 클라이언트
    participant 컨트롤러
    participant 서비스
    participant 레포지토리
    participant 데이터베이스

    사용자->>클라이언트: 게시물 조회 요청
    클라이언트->>컨트롤러: 쿼리 파라미터와 함께 HTTP GET /api/posts 요청
    컨트롤러->>서비스: 요청 유효성 검사
    서비스->>레포지토리: 필터, 검색, 정렬, 페이지네이션 적용하여 게시물 조회
    레포지토리->>데이터베이스: 쿼리 실행
    데이터베이스-->>레포지토리: 일치하는 게시물 반환
    레포지토리-->>서비스: 게시물 데이터 반환
    서비스->>컨트롤러: 응답 형식으로 데이터 정리
    컨트롤러-->>클라이언트: 게시물 목록 반환
    클라이언트-->>사용자: 게시물 사용자에게 표시

```

</details>


### **마일스톤 (Milestones)**

> `~8월 24(목)`: 요구사항 정리 및 문서화, controller, repository 계층 구현, queryDSL 공부
> 
> `~8월 25일(금)`: service 계층 구현 , 테스트 코드 추가
> 
> `~8월 26일(토)`: 코드 리팩토링

</details>

<details>
	<summary>통계 API</summary>

### **요약 (Summary)**

사용자가 지정한 파라미터에 맞는 게시물을 통계를 내어 반환합니다.

사용자는 `type`, `hashtag`, `value`, `start(조회 시작일)`, `end(조회 종료일)`를 지정할 수 있습니다.

### **목표 (Goals)**

- 쿼리 파라미터 사용
    - API에서 제공하는 쿼리 파라미터를 통해 통계 데이터를 요청하고, 유효성을 검사하여 올바른 결과를 반환합니다.
- 유효성 검사
    - `start`와 `end`의 날짜 유효성 확인 및 `type`에 따른 날짜 간격 제한을`(30일, 7일)` 설정합니다.
- 데이터 집계
    - 요청된 기간 내에 게시물의 조회수, 좋아요 수, 공유 수 등을 집계하여 반환하는 기능을 구현합니다.

### **목표가 아닌 것 (Non-Goals)**

- 통계 기능을 위한 테이블은 생성하지 않습니다.

### **계획 (Plan)**

#### QueryDSl을 사용한 조회

- 많은 쿼리 파라미터를 처리할 동적 쿼리 생성을 위해 `QueryDSL` 사용
- `type`이 `date`인 경우와 `hour`인 경우를 나눠서 메소드 작성
- `Expressions.dateTimeTemplate`을 사용하여 날짜 및 시간 반환 타입 변환

#### API 응답 형식
```json
# type이 date / value는 지정되지 않았으므로 count / hashtag는 springboot / star와 end는 지정되지 않았으므로 오늘로부터 7일 전 ~ 오늘
[
    {
      "date": "2024-08-19",
      "countByValue": 3
    },
    {
      "date": "2024-08-20",
      "countByValue": 10
    } # ... "2024-08-26"까지 data 반환
  ]
```
<details>
	<summary> 플로우 차트</summary>

```mermaid
graph TD
    A((쿼리 파라미터 요청)) --> B{유효성 검사}
    B --> |start가 end보다 나중| C((400 BAD_REQUEST 반환))
    B --> |type이 date이고 간격 30일 초과| D((400 BAD_REQUEST 반환))
    B --> |type이 hour이고 간격 7일 초과| E((400 BAD_REQUEST 반환))
    B --> |유효| F{post에서 데이터 반환}
    F --> G((데이터 반환))

```

</details>

<details>
	<summary> 클래스 다이어그램 </summary>

```mermaid
classDiagram
    class PostStatisticsController {
        + List<PostStatisticsListRes> getPostStatistics(String type, String hashtag, String value, LocalDate start, LocalDate end)
    }

    class PostStatisticsService {
        + List<PostStatisticsListRes> getPostStatistics(PostStatisticsListReq request)
    }

    class PostRepository {
        +PostStatistics findByPostId(Long postId)
        +void save(PostStatistics postStatistics)
    }

    class PostStatisticsCustomRepository {
        +List<PostStatisticsListRes> findPostStatisticsByQueryParameter(PostStatisticsListReq request)
        +List<PostStatisticsListRes> findPostStatisticsByQueryParameterWithHour(PostStatisticsListReq request);
    }

    PostStatisticsController --> PostStatisticsService
    PostStatisticsService --> PostRepository
    PostRepository --> PostStatisticsCustomRepository

```

</details>


</br>

### **마일스톤 (Milestones)**

> `~ 8.22(목)`: 요구 사항 분석, 테크 스펙 작성
> 
> `~ 8.23(금)`: 더미 데이터 생성. api 명세 구상
> 
> `~ 8.24(토)`: `dto`, `controller` 계층 작성
> 
> `~ 8.25(일)`: `service`, `repository`계층 기능 개발
> 
> `~ 8.26(월)`: Rollout
</details>

## 🏗️ ERD
<img src="https://github.com/user-attachments/assets/62f89985-2ddc-482e-a244-3ded19b240b6" width=600 />

  

## 🕝 프로젝트 진행 및 이슈 관리

- Github Project 칸반보드 활용
- 각 기능별 이슈 작성 후 하위 이슈 추가


<table>
    <tr align="center">
        <td><img width="1455" alt="스크린샷 2024-08-26 18 06 36" src="https://github.com/user-attachments/assets/b808e098-626d-4f34-a699-2ea8ddd55c0a"></td>
        <td><img width="1455" alt="스크린샷 2024-08-26 18 06 58" src="https://github.com/user-attachments/assets/ecc23acf-1850-4a87-8abc-97953ba1d8d0"></td>
</table>

## 👾 Authors

<table>
    <tr align="center">
        <td><B>주다애<B></td>
        <td><B>강경원<B></td>
        <td><B>이도은<B></td>
        <td><B>정의진<B></td>
        <td><B>조혜온<B></td>
        <td><B>이예림<B></td>
    </tr>
    <tr align="center">
            <td>
            <img src="https://github.com/jooda00.png?size=100">
            <br>
            <a href="https://github.com/jooda00"><I>jooda00</I></a>
        </td>
            <td>
            <img src="https://github.com/toughCircle.png?size=100">
            <br>
            <a href="https://github.com/toughCircle"><I>toughCircle<I></a>
        </td>
        <td>
            <img src="https://github.com/medoeun.png?size=100">
            <br>
            <a href="https://github.com/medoeun"><I>medoeun</I></a>
        </td>
        <td>
            <img src="https://github.com/uijin-j.png?size=100">
            <br>
            <a href="https://github.com/uijin-j"><I>uijin-j</I></a>
        </td>
        <td>
          <img src="https://github.com/hye-on.png?size=100">
            <br>
            <a href="https://github.com/hye-on"><I>hye-on</I></a>
        </td>
        <td>
          <img src="https://github.com/yerim123456.png?size=100">
            <br>
            <a href="https://github.com/yerim123456"><I>yerim123456</I></a>
        </td>
    </tr>

</table>
