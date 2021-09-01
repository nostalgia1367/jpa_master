package kr.co.jframework.jpa;


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kr.co.jframework.domain.Board;


/**
 * ####JPA 처리를 담당하는 Repository 인터페이스
 *
 *  일반적으로 DAO 개념을 이용하듯이, JPA에는 Repository 용어로 사용한다.
 *  Spring Data JPA 쪽에는 다음과 같은 인터페이스 구조를 사용하고 있다.
 *
 *  Repository<T,ID>
 * 		↑
 *  CrudRepository<T,ID>
 * 		↑
 *  PagingAndSortingRepository<T,ID>
 *
 *  모든 인터페이스가 <T, ID> 두개의 제네릭 타입을 사용하는 것을 볼 수 있으며,
 *  T는 type 을 가르키며,
 *  ID는 식별자(PK)을 가르킨다.
 *
 * PagingAndSortingRepository의 하위 인터페이스 JpaRepository
 * JPA에 특화된 몇 개의 기능을 추가적으로 가지고 있다.
 *
 *  ☆☆☆☆
 *  이때 ID 타입은 반드시 java.io.Serializable 인터페이스 타입이어야 한다.
 *
 *  가상 상위의 Repository 인터페이스는 사실상 아무 기능을 가지고 있지 않기 때문에
 *  그 하위에있는 CrudRepository와 PagingAndSortingRepository를 주로 사용한다.
 *
 *  #### Resository 인터페이스 설계해보자
 *
 * 		long		count()			모든 엔티티의 개수
 * 		void		delete(ID)		식별키를 이용한 삭제
 * 		void 		delete(Iterable<? extends T>) 주어진 모든 엔티티 삭제
 * 		void		deleteAll()		모든 엔티티 삭제
 * 		boolean		exists(ID)		식별키를 가진 엔티티가 존재하는지 확인
 * 		Iterable<T> findAll() 		모든 엔티티 목록
 * 		Iterable<T> findAll(Iterable<ID>) 해당 식별키를 가진 엔티티 목록 반환
 * 		T			findOne(ID)		해당 식별키에 해당하는 단일 엔티티 반환
 * 		<S extends T>Iterable<S> save(Iterable<S>) 해당 엔티티들의 등록과 수정
 * 		<S extends T>S save(S entity)	해당 엔티티의 등록과 수정
 *
 *
 */
public interface BoardJPA extends CrudRepository<Board, Long>{

	//쿼리 메소드를 사용하기 위하여 인터페이스 method 작성
	public List<Board> findBoardByTitle(String title);


	//작성자에 대한 like % 키워드 %
	public Collection<Board> findByWriterContaining(String writer);

	
	//OR 조건의 처리 -> '%' + 키워드 + '%'	Containing
	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);

	
	//부등호 처리 -> title LIKE % ? % And BNO > ?
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, Long num);

	
	// ORDER BY 처리 -> bno가 ? 보다 큰 게시물을 bno 값의 내림차순으로 정렬
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(long bno);

	
	// 페이징 처리와 정렬
	// bno > ? ORDER BY bno DESC limit ?, ?
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);

	//인터페이스에 페이징, 정렬 처리
//	public List<Board> findByBnoGreaterThan(Long bno, Pageable paging);

	//List<T> 타입을 하용하여 인터페이스에 페이징, 정렬 처리
	//> Spring MVC와 연동할 때 상당한 편리함을 제공
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);


	//@Query 문을 이용하여 JPQL 문 작성
	/**
	 * 앞으로 Boot03ApplicationTests2.java 로 실습파일 이동
	 * @Query는 JPQL(객체 쿼리)를 이용한다. JPQL은 JPA에서 사용하는 Query Lang이라고 보면 된다.
	 * JPA의 구현체에서 이를 해석해서 실행한다.
	 * JPQL의 가장 기본적인 형태는 테이블 대신에 엔티티 타입을 이용한다는 것과 컬럼명 대신에 엔티티 속성을 이용한다는 것이다.
	 * SQL에서 많이 사용되는 order by 나 group by 등은 기본적으로 지원한다.
	 *
	 * @Query 에서는 '%?1%' 부분을 눈여겨 봐야 한다.
	 * '?'는 JDBC 상에서 PreparedStatement와 동일하다고 생각하면 되고
	 * '?1'은 첫 번째로 전달되는 파라메터라고 생각하면 된다.
	 */
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);

	//108p @Query와 @Param을 이용한 내용 검색 처리
	/**
	 * @Param 은 o.sfw.data.repository.query.Param 을 import
	 *
	 * @param content
	 * @return
	 */
	@Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByContent(@Param("content") String content);

	//작성자에 대한 검색 처리 #{#entityName}
	@Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByWriter2(String writer);

	//필요한 컬럼명만 추출하는 경우
	/**
	 * @Query 를 이용하면 필요한 칼럼만을 추출할 수 있다.
	 * 특이한 점은 리턴 타입이 엔티티 타입이 아닌 Object[] 의 리스트 형태로 작성된다는 점이다.
	 */
	@Query("SELECT b.bno, b.title, b.writer, b.regdate "
			+"FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);

	//nativeQuery 사용으로 좀 더 쉽게 사용하기
	/**
	 * @Query에 vativeQuery 속성을 'true'라고 지정하면 된다.
	 */
	@Query(value="SELECT bno, title from tbl_boards where title like "
			+ "CONCAT('%',?1,'%') AND bno > 0 ORDER BY bno DESC", nativeQuery=true)
	public List<Object[]> findByTitle3(String title);

	
}
