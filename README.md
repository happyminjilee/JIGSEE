# **⚙ Jig 생애주기 관리 프로그램 - JIG:SEE ⚙**

> Samsung Software Academy For Youth 10기  
> Samsung SDI 기업 연계 프로젝트  
> 개발 기간 : 2024.04.08 ~ 2024.05.20

<br>

# 1. 프로젝트 배경

<strong>[배경]</strong>

<p>제품과 직접 맞닿아있는 JIG는 제품의 품질에 큰 영향을 미칩니다.
기존 수기로 관리되고 있던 JIG는 작업자의 실수로 설비에 잘못된
JIG가 투입되는 문제가 발생할 수 있고, 보수 처리되지 않은 JIG가
다시 재투입되는 문제가 발생할 수 있습니다. 또한, 이력을 관리하기 어
려워 JIG의 Spec과 품질에 연관성을 분석하기 어렵다는 불편함이 있습니다. 이와 같은 JIG에 대한 관리를 시스템을 통해 개선하고자 합니다.</p>

<br>
<strong>[목표]</strong>
<p>- JIG 프로세스 상태에 따른 관리 기능 구현
</p>
<p>- 품질 분석 및 이력 조회를 위한 레포트 화면 구현
</p>
<p>- 설비 실적 인터페이스 및 제어 기능
</p>
<br>

# 2. 주요 기능

## 관리자 화면

### 대시보드

<img src="./readme_assets/대시보드1.gif" style="width:550px;">

<img src="./readme_assets/대시보드2.gif" style="width:550px;">

- 전체 Jig 현황, 예상 점검 주기, 점검 주기 별 생산량, 위험 지그 수를 한 눈에 파악할 수 있습니다.

<br>

### Jig 불출 요청 및 불출 승인

<img src="./readme_assets/지그_불출_요청1.gif" style="width:550px;">

- 관리자가 직접 Jig의 불출을 요청할 수 있습니다.

<img src="./readme_assets/지그_불출_반려1.gif" style="width:550px;">

- 불출 요청된 Jig 목록을 확인하고 승인, 반려할 수 있습니다.

<img src="./readme_assets/지그_불출_승인여부_확인1.gif" style="width:550px;">

- 승인이나 반려된 Jig 목록을 확인할 수 있습니다.

<br>

### Work Order

<img src="./readme_assets/점검항목_수정.gif" style="width:550px;">
<img src="./readme_assets/wo_확인.gif" style="width:550px;">

- Jig의 점검 항목을 수정할 수 있습니다.
- 수정된 점검 항목은 이후 작성되는 Work Order에 자동으로 반영됩니다.

<br>

## 기술팀 화면

### 대시보드

<img src="./readme_assets/기술팀_대시보드1.gif" style="width:550px;">
<img src="./readme_assets/기술팀_대시보드2.gif" style="width:550px;">

- 각 Jig의 상세 정보, 수리 현황을 한 눈에 볼 수 있습니다.

<br>

### Jig 불출 요청

<img src="./readme_assets/기술팀_Jig_불출_요청.gif" style="width:550px;">

- 필요한 Jig의 불출을 요청하고 요청 내역을 확인할 수 있습니다.

<br>

### Jig 수리 내역

<img src="./readme_assets/기술팀_수리내역.gif" style="width:550px;">

- Jig의 수리 진행 내역을 조회하고 수리가 완료된 Jig의 Work Order를 작성할 수 있습니다.

<br>

## 생산팀 App

|                                                          |                                                           |                                                                 |
| :------------------------------------------------------: | :-------------------------------------------------------: | :-------------------------------------------------------------: |
| <img src="./readme_assets/app.gif" style="width:550px;"> | <img src="./readme_assets/app2.gif" style="width:550px;"> | <img src="./readme_assets/app2-check.gif" style="width:550px;"> |

- 생산팀은 교체 주기가 된 Jig의 목록을 확인할 수 있습니다.
- 간단하게 S/N을 촬영하고 해당 Jig를 교체할 수 있습니다.
- Jig의 잘못된 투입을 방지합니다.

<br>

# 3. 백엔드 기술

## K8S

## MSA

- Q1) 왜 MSA 구조를 도입했나요?
- Q2)

### 프록시 서버(API Server)

- Q1) 어떤 역할을 하나요?
- Q2) 사용한 기술이 뭔가요?

### Jig 상태 관리 서버(Jig Server)

- Q1) 어떤 역할을 하나요?
- Q2) 사용한 기술이 뭔가요?

### 회원 서버(Member Server)

- Q1) 어떤 역할을 하나요?
- Q2) 사용한 기술이 뭔가요?

### 알림 서버(Notification Server)

- Q1) 어떤 역할을 하나요?
  > Jig 불출 요청과 승인, Jig의 보수 요청 등 부서와 부서 간 요청/응답을 빠르게 확인할 수 있도록 알림을 전송합니다.  
  > 정기 점검 기간에 점검해야 할 Jig의 리스트를 사용자의 앱 푸시 알림, 이메일로 전송합니다.  
  > 알림의 전송 및 알림 내용 확인 등 알림과 관련된 모든 기능을 수행합니다.
- Q2) 사용한 기술이 뭔가요?
  > 백엔드 REST API 서버로 Spring Boot를 사용했습니다.  
  > 부서 간 요청/응답을 실시간으로 확인할 수 있도록 Server-Sent-Events를 활용했고,  
  > Replica가 3인 쿠버네티스 환경에서 SSE의 Server 의존 문제를 해결하기 위해 Redis Pub/Sub을 도입했습니다.  
  > 전송한 알림을 MySQL에 저장해 언제든지 확인할 수 있게 했습니다.  
  > 정기 점검 리스트를 앱으로 전송하기 위해 Firebase Cloud Messaging(FCM)을 사용했고, 이메일 전송은 Gmail의 SMTP 서버를 사용했습니다.

### 회원 간 요청/응답 서버(Notification-API Server)

- Q1) 어떤 역할을 하나요?
  > Jig 불출 요청과 승인, Jig의 보수 요청 등 부서와 부서 간 요청/응답 과정을 담당합니다.  
  > 요청/응답 내역의 확인을 담당합니다.
- Q2) 사용한 기술이 뭔가요?
  > 백엔드 REST API 서버로 Spring Boot를 사용했습니다.  
  > 다양한 요청/응답 내용을 저장하기 위해 NoSQL인 MongoDB를 사용했습니다.

### 주기 점검 리스트 생성 서버(Watching Server)

- Q1) 어떤 역할을 하나요?
- Q2) 사용한 기술이 뭔가요?

### 작업지시서 관리 서버(Work-Order Server)

- Q1) 어떤 역할을 하나요?
- Q2) 사용한 기술이 뭔가요?

<br>
<br>

# 4. 프로젝트 구조

## 4-1. 기술 스택

<div align="center">
<h1>✨Front-end Stack✨</h1>
<img src="https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-F68212?style=for-the-badge&logo=CSS3&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"/>
<img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=TypeScript&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Node.js-%5FA04E?style=for-the-badge&logo=node.js&logoColor=white"/>
<img src="https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white"/>
<img src="https://img.shields.io/badge/zustand-A30701?style=for-the-badge&logo=zustand&logoColor=white"/>
<img src="https://img.shields.io/badge/AXIOS-%235A29E4?style=for-the-badge&logo=axios&logoColor=white"/>
<img src="https://img.shields.io/badge/flutter-02569B?style=for-the-badge&logo=flutter&logoColor=white"/>
</div>

<br>

<div align="center">
<h1>✨Back-end Stack✨</h1>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/mongodb-47A248?style=for-the-badge&logo=mongodb&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"/>
<img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white"/>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/firebase fcm-FFCA28?style=for-the-badge&logo=firebase&logoColor=white">
<br>
<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/Jpa-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/>
</div>

<br>

<div align="center">
<h1>✨Infrastructure Stack✨</h1>
<img src="https://img.shields.io/badge/DOCKER-%232496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/Aws%20Certificate%20Manager-%23232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/AMAZON%20ECR-FF9900?style=for-the-badge&logo=amazonecr&logoColor=white">
<br>
<img src="https://img.shields.io/badge/amazon%20eks-FF9900?style=for-the-badge&logo=amazoneks&logoColor=white">
<img src="https://img.shields.io/badge/JENKINS-%23D24939?style=for-the-badge&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white">
</div>

<br>

<div align="center">
<h1>✨Cooperation Tool✨</h1>
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"/>
<img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white"/>
<img src="https://img.shields.io/badge/gerrit-A9ffA9?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white"/>
<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white"/>
</div>

<br>

## 4-2. API 명세서

<img src="./readme_assets/api.png" style="width:550px;">

<br>

<img src="./readme_assets/api_1.png" style="width:550px;">
<img src="./readme_assets/api_2.png" style="width:550px;">

<br>

<img src="./readme_assets/api_3.png" style="width:550px;">
<img src="./readme_assets/api_4.png" style="width:550px;">

<br>

<img src="./readme_assets/api_5.png" style="width:550px;">

<br>

<img src="./readme_assets/api_6.png" style="width:550px;">

<br>

## 4-3. ERD

### MySQL

<img src="./readme_assets/MySQL-ERD.png" style="width:550px;">

### MongoDB

<img src="./readme_assets/Mongo-ERD.png" style="width:550px;">

<br>

## 4-4. 시스템 아키텍처

<img src="./readme_assets/msa.png" style="width:550px;">

- MSA 구조로 Proxy Server가 클라이언트-서버 / 서버-서버 간의 통신을 담당

<img src="./readme_assets/architecture.png" style="width:550px;">

<br>

## 4-5. 화면 정의서

<img src="./readme_assets/figma_0.png" style="width:550px;">
<img src="./readme_assets/figma_1.png" style="width:550px;">
<img src="./readme_assets/figma_2.png" style="width:550px;">
<img src="./readme_assets/figma_3.png" style="width:550px;">

<br>
<br>

# 5. 팀 구성

<table border="1" cellpadding="1" cellspacing="1" style="width:700px">
	<thead>
		<tr>
			<th scope="col" style="text-align: center;"><strong>이름</strong></th>
			<th scope="col" style="text-align: center;"><strong>역할</strong></th>
			<th scope="col" style="text-align: center;"><strong>담당</strong></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td style="text-align: center;">주준형</td>
			<td style="text-align: center;">팀장, FE, 발표</td>
			<td>
			DnD component 제작<br>
            Zustand 초기 설정<br>
            jwt 토큰 로그인<br>
            프론트엔드 웹 배포<br>
            로그 데이터 분석
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">이민지</td>
			<td style="text-align: center;">팀원, FE, UCC 제작</td>
			<td>
			Web UI 개발<br>
            현황판 구현<br>불출요청, test result 컴포넌트 구현<br>
            .env환경 설정
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">박수형</td>
			<td style="text-align: center;">팀원, FE, 시연</td>
			<td>
			Flutter 앱 개발<br>
            전체적인 UI/UX 가이드 제작<br>
            Next.js 초기 설정<br>
            로그데이터 생성<br>
            웹 라우터 가드
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">차현철</td>
			<td style="text-align: center;">팀원, BE</td>
			<td>
			회원 서버 개발<br>
            MSA 아키텍처 구현<br>
            API 명세서 및 ERD 관리
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">강성범</td>
			<td style="text-align: center;">팀원, BE</td>
			<td>
			서버 개발<br>
            CI/CD 구축<br>
            쿠버네티스 구축<br>
            MSA 아키텍처 설계 및 구현
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">김용준</td>
			<td style="text-align: center;">팀원, BE</td>
			<td>
			알림 서버, 불출 및 보수 요청/응답 서버 개발<br>
            CI/CD 적용<br>
            MySQL 및 MongoDB를 활용하여 알림 내역 저장<br>
            SSE, FCM, SMTP로 다양한 알림 구현
			</td>
		</tr>
	</tbody>
</table>
