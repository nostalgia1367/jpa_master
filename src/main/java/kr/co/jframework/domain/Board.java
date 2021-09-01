package kr.co.jframework.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Id 어노테이션 속성
 * 각 엔티티를 구별할 수 있는 식별 ID
 *
 * @Column 어노테이션 속성
 * name			String		칼럼 이름
 * unique 		boolean 	유니크 여부
 * nullable		boolean 	null 허용 여부
 * inserttable	boolean 	insert 가능 여부
 * updatable 	boolean 	수정 가능 여부
 * table		String		테이블 이름
 * length		int 		사이즈				255
 * precision	int			소수 정밀도			0
 * scale		int			소수점 이하 자리수
 *
 * @Table
 * 보통적으로 클래스이름이 테이블명이 되며,
 * 클래스의 선언부에 @Table 어노테이션을 작성
 * 만약 @Table이 지정되지 않으면 클래스 이름과 동일한 이름의 테이블이 생성
 *
 * name 		String		테이블이름
 * catalog		String		테이블 카테고리
 * schema		String		테이블 스키마
 * uniqueConstraints 	UniqueConstraint[]		칼럼값 유니크 제약 조건
 * indexs		Index[]		인덱스 생성
 *
 * 클래스 선언부에는 반드시 @Entity 가 설정되어야 한다.
 * @Entity 설정은 해당 클래스가 '엔티티 클래스 임을 명시'한다
 *
 * 오라클의 경우에는 Sequence(시퀀스) 사용하고, MySQL은 Auto Increment를
 * 사용하므로 각 데이터베이스마다 참고해야한다.
 *
 * @Id는 @GeneratedValue라는 어노테이션과 같이 이용해서 식별키를 어떻게
 * 생성하는지를 명시한다.
 *
 * @GeneratedValue는 strategy 속성과 generator 속성으로 구분된다.
 *
 * strategy : AUTO, TABLE, SEQUENCE, IDENTITY
 * generator : @TableGenerator, @SequenceGenerator
 *
 * JPA와 관련된 import 시에 동일한 이름의 다른 패키지가 존재하니 추가시 주의
 *
 * import javax.persistence.Entity; 형식으로 되어있다.
 * import javax.persistence.Table;
 *
 *
 * GenerationType.AUTO 와 GenerationType.INDENTITY 차이
 *  * 엔티티의 식벽키를 처리하는 여러 방식(전략) 중에서도 GenerationType.AUTO는
 *  * 데이터베이스에 맞게 자동으로 식별키를 처리하는 방식으로 동작한다.
 *  *
 *  * MySQL의 경우 스프링 부트 1.5.4버전을 이용할 때 AUTO로 지정하면 컬럼이 auto_increment로 지정되었다.
 *  *
 *  * 반면에 2.0.0.M1 버전의 경우 AUTO로 지정하면 hibernate_sequence라는 테이블을 생성하고 번호를
 *  * 유지하는 방식으로 변경되었다.
 *  *
 *  * 앞서 Profile 클래스의 @Id 생성 타입을 GenerationType.AUTO로 지정하면
 *  * 다음과 같이 hibernate_sequence라는 테이블이 생서오디는 것을 볼 수 있다.
 *
 *
 *
 *
 *
 *
 *
 */
@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_boards")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //AUTO => auto_increment 라고 생각하면 됨.
	private Long bno;
	private String title;
	private String writer;
	private String content;

	//@@CreationTimestamp 은 javax.persistence가 아닌 org.hibernate의 고유 패키지로써
	//엔티티가 생성되거나 업데이트 되는 시점에 날짜 데이터를 기록하는 설정이다.
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;

}
