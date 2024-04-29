# 회원 가입 `ver.5`

## 💬 소개

### 회원 가입

1. `login.jsp`에서 `<button>`을 누르면 폼을 전송한다.

2. `MemberController`의 `signUp()`: `POST` 방식을 이용해 폼에 적은 정보들이 `MemberController`로 전달된다. 이때 `@ModelAttribute`가 전달된 폼의 `name`과 `SignUpForm`의 필드에 맞게 바인딩되도록 한다.

3. `MemberService`의 `signUp()`

   - `verifyUsernameLength()`: `username`의 길이가 6 이하인지 검증

     → 검증에 실패하면 `false` 리턴

   - `verifyUsernameDuplicates()` → `cntMemberByUsername()`: 중복된 `username`이 `DB`에 있는지 검증, `cntMemberByUsername()`의 값이 `1` 이상이면 검증에 실패

     → 검증에 실패하면 `false` 리턴

- `encPassword()`: 패스워드 암호화

4. `MemberDao`의 `signUp()`

   - `cntMemberByUsername()`: `username`과 `member`의 `member_id`가 일치하는 개수를 리턴

5. 마지막 `signUp()`를 통해 전달된 정수 값에 따라 `login.jsp`에 전달될 `msg` 값을 달리한다.

   - `1`: `Model`에 `msg` 값 `"회원가입에 성공했습니다."`을 할당
   - `-1`: `Model`에 `msg` 값 `"회원 ID는 7자 이상이어야 합니다."`을 할당
   - `-2`: `Model`에 `msg` 값 `"중복 등록된 ID입니다."`을 할당

6. 이후 `1.`의 동일한 `login.jsp` 페이지에서 `Model`로부터 넘겨 받은 `msg`의 값을 통해 매시지를 `alert()` 해준다.

7. 회원 가입 페이지로 이동한다. (`location.href='/11005/join-page.do'`)

### 로그인

1. `login.jsp`에서 `<button>`을 누르면 폼을 전송한다.

2. `MemberController`의 `login()`: `POST` 방식을 이용해 폼에 적은 정보들이 `MemberController`로 전달된다. 이때 `@ModelAttribute`가 전달된 폼의 `name`과 `LoginForm`의 필드에 맞게 바인딩되도록 한다.

3. `MemberService`의 `login()` → `findEncPasswdByUsername()`

   - 폼을 통해 넘겨 받은 `password`를 암호화한 한 후 `findEncPasswdByUsername()`의 리턴 값과 일치하는지 확인한다.

     → 검증에 실패하면 `false` 리턴

4. `MemberDao`

   - `findEncPasswdByUsername()`: `username`과 `member`의 `member_id`가 일치하는 행의 `passwd`를 리턴

5. 마지막 `join()`를 통해 전달된 값에 따라 `login.jsp`에 전달될 `msg` 값을 달리한다.

   - `true`: `Model`에 `msg` 값 `"로그인에 성공했습니다."`을 할당

   - `false`: `Model`에 `msg` 값 `"로그인에 실패했습니다."`을 할당

6. 이후 `1.`의 동일한 `login.jsp` 페이지에서 `Model`로부터 넘겨 받은 `msg`의 값을 통해 매시지를 `alert()` 해준다.

7. 회원 가입 페이지로 이동한다. (`location.href='/11005/join-page.do'`)

### 회원 인증

#### 회원 가입 시 인증 메일 발송하기

1. `MemberService`의 `signUp()`의 모든 검증이 끝난 후부터 시작한다.

2. `member_auth` 인증에 필요한 정보 `MemberAuthDto` 만든 후 `member_auth` 테이블에 삽입: `makeMemberAuthDto()` → `addMemberAuthInfo()`

3. 인증 메일 전송에 필요한 `EmailDto` 만든 후 인증 메일 보내기: `makeEmailDto()` → `sendEmail()`

   → 전송에 실패하면 `-3` 리턴

#### 인증 완료 후 인증 여부 반영

1. 회원이 이메일 속 링크를 클릭하면 `/emailAuth.do?uri=...`로 연결된다.

2. `MemberService`의 `emailAuth()`는 현재 시간과 `member_auth`의 `expire_dtm`을 비교해 인증 만료 시간이 지나지 않은 경우에만 `auth_yn`을 `'Y'`로 변경한다.

## 🔨 기능 요구사항

### 프로젝트 환경 설정하기

- [x] servlet

- [x] spring

- [x] spring jdbc

- [x] logback

### 기존 소스 변경 사항

- [x] `index.html`을 `login.jsp` 로 변환

- [x] css, js 파일 resources tag 설정

  - [x] 프로젝트 컨텍스트 루트를 `<c:url/>` `JSTL` 태그를 이용해 모든 경로 앞에 추가해줌

### Servlet 구성 및 접속

- [x] 회원가입 페이지 : `/join-page.do`

- [x] 회원가입 : `/sign-up.do`

  - [x] 회원 ID 6자 이하 가입 불가

  - [x] 회원 ID 중복 확인해보기

  - [x] 회원 테이블에 정보 입력하기

  - [x] 회원 인증

    - [x] 회원 가입 시 인증 이메일 발송하기

    - [x] 인증 완료 후 인증 여부 반영

  - [x] 회원가입 성공/실패에 따른 `alert()` 노출하기

- [x] 로그인 : `/login.do`

  - [x] 로그인 성공/실패에 따른 `alert()` 노출하기

### 예외 처리

- [x] `/emailAuth.do`의 `uri` 값이 빈 문자열이거나 `null`인 경우 (추후 보완 예정)

- [x] `MemberAuthDao`의 `findMemberAuthByUri`의 리턴 값이 `null`인 경우 (추후 보완 예정)

### 기타

- [x] `JavaMailSender`의 `username`과 `password` 암호화

## 🐿️ Docker DB

```
# for Windows
docker run --name mysql-lecture -p 53306:3306 -v c:/dev/docker/mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=admin_123 -d mysql:8.3.0

# for Mac
docker run --name mysql-lecture -p 53306:3306 -v ~/dev/docker/mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=admin_123 -d mysql:8.3.0
```

## 🚨 트러블 슈팅

### RedirectAttributes 사용 시 한글 깨짐

#### 문제 상황

`JoinController`에서 회원가입 성공 실패 여부에 따른 메시지(`msg`)를 `RedirectAttributes`의 `addAttribute`를 통해 추가해주었다. 하지만 `login.jsp` 페이지에서는 `encoding`된 상태로만 메시지가 뜬다.

#### 해결 방법

- [ ] `msg`를 `URLEncoder.encode`를 사용해 `UTF-8`로 인코딩

  [참고] https://ivvve.github.io/2019/01/20/java/Spring/redirect_URL_encoding/

- [ ] javascript에 `charset="utf-8` 추가

  [참고] https://whitekeyboard.tistory.com/445

- [ ] `web.xml`에 한글 변환 필터 넣기

  [참고1] https://homesi.tistory.com/entry/Spring%EC%97%90%EC%84%9C-%ED%95%9C%EA%B8%80%EA%B9%A8%EC%A7%90-%EB%B0%A9%EC%A7%80%EB%A5%BC-%EC%9C%84%ED%95%B4-webxml%EC%97%90%EC%84%9C-%ED%95%9C%EA%B8%80-%EC%84%A4%EC%A0%95

  [참고2] https://taetoungs-branch.tistory.com/134

### SpringJdbc `sql` 쿼리 문법 오류

#### 문제 상황

`sql`을 `String` 값으로 전달해야 하기 때문에 중간에 값을 넣기 위해 `" + 값 + "`와 같이 문자열을 구성하면서 에러가 많이 나게 된다.

```
nested exception is org.springframework.jdbc.BadSqlGrammarException: StatementCallback; bad SQL grammar [INSERT INTO forum.`member` (member_id, passwd, member_nm, email, pwd_chng_dtm, join_dtm) VALUES('hakie2kim@gmail.com', '$2a$12$tUwcm5tvODMlxUNFBWIN/u7tKNwmqeTJVd5IdUFDcvmYvXkiFoVPe', 'HAKJUN KIM', 'hakie2kim@gmail.com', 'DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), DATE_FORMAT(NOW(), '%Y%m%d%H%i%s');]; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ''), DATE_FORMAT(NOW(), '%Y%m%d%H%i%s')' at line 1]을(를) 발생시켰습니다.
```

#### 해결 방법

- [x] DATE_FORMAT 앞 ' 잘못 추가, 마지막에 닫는 괄호 추가

### 빈 생성 도중 에러 발생

#### 문제 상황

`joinDao` 빈을 만드는데 에러가 발생했다.

```
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'joinDao' defined in file [C:\dev\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\110_Join_02\WEB-INF\classes\com\portfolio\www\dao\JoinDao.class]: Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: Property 'dataSource' is required
```

#### 해결 방법

- [x] `JoinDao`의 `DataSource`를 `javax.sql`에서 `import` 하도록 수정

- [x] `DataSource dataSource` 필드에 `@Autowired` 의존성 주입 추가

### `sql` 쿼리 문법 오류 (1)

#### 문제 상황

처음에 `sql` 값으로 `"SELECT * FROM ..."`을 했더니 다음과 같은 에러가 나왔다.

```
Request processing failed; nested exception is org.springframework.jdbc.IncorrectResultSetColumnCountException: Incorrect column count: expected 1, actual 8
org.springframework.jdbc.IncorrectResultSetColumnCountException: Incorrect column count: expected 1, actual 8
com.portfolio.www.dao.JoinDao.findMemberByUsername(JoinDao.java:27)
```

`member`의 모든 칼럼(8개)을 담을 객체를 `queryForObject()`의 두번째 인자로 제공했어야 한다.

#### 해결 방법

- [x] `queryForObject()`의 두번째 인자로 `Integer.class`를 제공하고 쿼리를 `SELECT COUNT(*) FROM ...`으로 변경해 해당 `member`가 몇 명 있는지 알아냄.

### `sql` 쿼리 문법 오류 (2)

#### 문제 상황

다음과 같은 에러가 발생했다.

```java
13 String sql = String.format("INSERT INTO forum.member_auth "
14				+ "(member_seq, auth_num, auth_uri, reg_dtm, expire_dtm, auth_yn) "
15				+ "VALUES(%d, '', %s, DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), %d, 'N'); ", dto.getMemberSeq(), dto.getAuthUri(), dto.getExpireDtm());
```

```
org.springframework.web.util.NestedServletException: Request processing failed; nested exception is java.util.UnknownFormatConversionException: Conversion = 'Y'
...
com.portfolio.www.dao.MemberAuthDao.addMemberAuthInfo(MemberAuthDao.java:13)
...
```

`String.format()`을 사용했을 때 `%Y`를 형식 지정자로 인식해 변환하려 한 것 같다.

### `queryForObject()`

#### 문제 상황

```java
24 public MemberAuthDto findMemberAuthByUri(String uri) {
25   String sql = String.format("SELECT * FROM forum.member_auth WHERE auth_uri = '%s'; ", uri);
26   return queryForObject(sql, MemberAuthDto.class);
  // ...
```

```
org.springframework.jdbc.IncorrectResultSetColumnCountException: Incorrect column count: expected 1, actual 7
...
com.portfolio.www.dao.MemberAuthDao.findMemberAuthByUri(MemberAuthDao.java:26)
```

#### 해결 방법

`queryForObject(String sql, Class<T> requiredType)`의 두번째 인자는 기본형 래퍼 클래스만 가능하다. 따라서 다음과 같이 다른 메서드를 사용하였다.

```java
return query(sql, new ResultSetExtractor<MemberAuthDto>() {
  public MemberAuthDto extractData(ResultSet rs) throws SQLException, DataAccessException {
    MemberAuthDto dto = null;

    if (rs.next()) {
      dto = new MemberAuthDto();
      dto.setAuthSeq(rs.getInt("auth_seq"));
      dto.setMemberSeq(rs.getInt("member_seq"));
      dto.setAuthNum(rs.getString("auth_num"));
      dto.setAuthUri(rs.getString("auth_uri"));
      dto.setRegDtm(rs.getString("reg_dtm"));
      dto.setExpireDtm(rs.getLong("expire_dtm"));
      dto.setAuthYn(rs.getString("auth_yn"));
    }

    return dto;
  }
});
```

### `sql` data truncation

#### 문제 상황

시간을 대소비교 할 때는 숫자로 비교하는 것이 가장 편하기 때문에 기존 `member_auth`의 `expire_dtm`의 데이터 타입을 `INT`로 변경했다.

```
Request processing failed; nested exception is org.springframework.dao.DataIntegrityViolationException: StatementCallback; SQL [INSERT INTO forum.member_auth (member_seq, auth_num, auth_uri, reg_dtm, expire_dtm, auth_yn) VALUES(56, '', 'e67ff01ee499423285426bbb44d05df5', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), 1713673255884, 'N');]; Data truncation: Incorrect datetime value: '1713673255884' for column 'expire_dtm' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Incorrect datetime value: '1713673255884' for column 'expire_dtm' at row 1
```

Data truncation이라는 단어에서 알 수 있듯이 `1713673255884`을 datetime 타입으로 변경하려다 값의 범위를 넘어 데이터 일부가 소실된다는 에러이다.

#### 해결 방법

`member_auth`의 `expire_dtm`의 데이터 타입을 더 큰 값의 범위를 담을 수 있는 `BIGINT`로 변경했다.

## 📝 메모

### `bean` 수동 등록 방법

`bean`을 수동으로 등록하는 방법에는 다음과 같이 두 가지가 있다.

1. 필드 사용

```xml
<bean id="joinDao" class="com.portfolio.www.dao.JoinDao">
  <property name="dataSource" ref="dataSource" />
</bean>
```

- `name`: 주입 받을 `JoinDao`의 필드(멤버 변수) 이름

- `ref`: `JoinDao`의 필드(멤버 변수) `dataSource`의 타입

2. 생성자 사용

```xml
<bean id="joinDao" class="com.portfolio.www.dao.JoinDao">
  <constructor-arg ref="dataSource" />
</bean>
```

### PRG (Post/Redirect/Get)

`login.jsp`에서 `location.href="/11002/joinPage.do"`를 하는 이유는 무엇일까? 만약 `login.jsp`에서 `alert()` 메시지를 띄운 후 페이지 이동(`location.href`)을 하지 않았다고 해보자. 회원 등록을 한 페이지에서 새로 고침을 한 후 `DB`를 확인 해보면 회원이 또 추가됐다는 것을 알 수 있다. 즉, 새로고침을 할 때마다 기존 입력한 회원이 계속 추가되는 것이다. 왜 이런 현상이 발생하는 것일까?

결론부터 말하자면 웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송하는 작업을 한다. 그렇기 때문에 `POST /join.do` + `회원 가입 폼에서 입력한 회원 데이터` 이 작업이 계속해서 반복된다. 따라서 회원 내용은 같고 `member_seq`만 증가한 `Member`의 데이터가 계속 추가된다. 여기에서 왜 `location.href`를 통해 페이지 이동을 하는지 알 수 있다. 다시 회원 가입 폼(`/joinPage.do`)으로 이동하게 되면 아무리 새로고침을 해도 웹 브라우저는 그저 회원 가입 폼만을 보여주게 된다.

위와 같은 방식을 `Post/Redirect/Get` 줄여서 `PRG`라 하며 지금은 `login.jsp`에서 직접 페이지를 회원 가입 폼(`/joinPage.do`)으로 이동했지만 `Spring`의 `redirect:`와 더불어 `RedirectAttributes` 기능을 사용하게 되면 폼 전송 후 자동으로 redirect 하게 된다.

### `setText()` 메서드 오버로딩

`EmailUtil`의 메서드 `sendMail()`을 보면 메서드가 오버로딩 되어있는 것을 볼 수 있다. 왜 그런 것일까?

회원이 클릭할 수 있는 링크가 담긴 이메일을 받기 위해서는 이메일 본문을 `HTML` 태그 형식으로 보내야 한다. 그렇게 하기 위해서는 우선 `MimeMessageHelper`의 `setText`메서드를 살펴봐야 한다.

```java
public void setText(String text) throws MessagingException {
  setText(text, false);
}

/**
	 * Set the given text directly as content in non-multipart mode
	 * or as default body part in multipart mode.
	 * The "html" flag determines the content type to apply.
	 * <p><b>NOTE:</b> Invoke {@link #addInline} <i>after</i> {@code setText};
	 * else, mail readers might not be able to resolve inline references correctly.
	 * @param text the text for the message
	 * @param html whether to apply content type "text/html" for an
	 * HTML mail, using default content type ("text/plain") else
	 * @throws MessagingException in case of errors
	 */
public void setText(String text, boolean html) throws MessagingException {
  // ...
}
```

주석을 보면 `@param html`에 대한 설명이 있는데 요약하면 다음과 같다.

- `content type "text/html"`의 형식 → `html`의 값: `true`
- `content type "text/plain"`의 형식 → `html`의 값: `false`

실제로도 매개 변수가 하나만 있는 `setText()`를 사용하면 `text/plain` 형식으로 `html` 본문이 구성된다. 이때, `setText(String text)` 안에는 `setText(String text, boolean html)`가 있는 것을 확인할 수 있다. 이와 같이, 어떤 메서드의 어떤 파라미터를 기본값으로 지정(`text/plain`)해주고 싶을 때와 아닌 경우를 구별할 때 이러한 메서드 오버로딩 방식이 많이 사용된다.
