# integrated-feed-backend

소셜 미디어 통합 Feed 서비스

## 요구사항 정리 및 기술 명세서

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

# **마일스톤 (Milestones)**

> `~ 8.22(목)`:  controller 계층 구현 <br>
`~ 8.23(금)`:  infra 계층의 adapter,client 구현 <br>
`~ 8.24(토)`: service 구현, 단위 테스트 작성 <br>
`~ 8.25(일)`: Rollout
>

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

# **마일스톤 (Milestones)**

> `~8월 24(목)` : 요구사항 정리 및 문서화, controller, repository 계층 구현, queryDSL 공부
> `~8월 25일(금)` : , service 계층 구현 , 테스트 코드 추가
> `~8월 26일(토)` :코드 리팩토링

</details>

## Authors

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
            <a href="https://github.com/uijin-js"><I>uijin-js</I></a>
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
