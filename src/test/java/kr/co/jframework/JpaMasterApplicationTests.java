package kr.co.jframework;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.jframework.domain.Board;
import kr.co.jframework.jpa.BoardJPA;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaMasterApplicationTests {

	@Autowired
	private BoardJPA boardRepo;

//	@Test
//	public void testInsert100ea() {
//		for (int i = 1; i <= 100; i++) {
//			Board board = new Board();
//			board.setTitle("제목..."+i);
//			board.setContent("내용.... "+i+" 채우기");
//			board.setWriter("작성자0"+(i%10));
//			boardRepo.save(board);
//		}
//	}

	//01. 쿼리메소드를 이용한 title
	// find테이블명By컬럼명
//	@Test
//	public void testByTitle() {
//
//		//자바 8버전 이하
//		List<Board> list = boardRepo.findBoardByTitle("제목...11");
//
//		for(int i=0, len = list.size(); i < len; i++) {
//			System.out.println("리스트: "+list.get(i));
//		}
//
//		//자바 8버전
////		boardRepo.findBoardByTitle("제목...117")
////		.forEach(board -> System.out.println(board));
//	}

	//02. 쿼리메소드를 이용한 like문 예제
	// findBy컬럼명Containing
//	@Test
//	public void testByTitleLike() {
//
//		Collection<Board> results = boardRepo.findByWriterContaining("05");
//
////		//자바 8버전 이하 > size() 이용
////		ArrayList<Board> arrList = (ArrayList<Board>) results;
////
////		for(int i=0; i < arrList.size(); i++) {
////			System.out.println("ArrayList값 찍기"+arrList.get(i));
////		}
//
//		//자바 8버전 이하 > Iterator 이용
//		ArrayList<Board> arrList2 = (ArrayList<Board>) results;
//		Iterator<Board> iter = arrList2.iterator();
//		while(iter.hasNext()) {
////			System.out.println("iter를 이용하여 값찍기:"+iter.next());
//			Object obj = iter.next();
//	        System.out.println("iter를 이용하여 값찍기:"+obj);
//		}
//
//		//자바 8버전 > forEach() 이용
//		//for 루프 대신 forEach
//		//results.forEach(board -> System.out.println("like구문 :'%'+키워드+'%' "+board));
//
//	}

	//03. 쿼리메소드를 이용하여 writer검색
	// findBy컬럼명
//	@Test
//	public void testByWriter() {
//		Collection<Board> results = boardRepo.findByWriter("작성자01");
//
//		results.forEach(board -> System.out.println("testByWriter() 리턴-> Collection: "+board));
//	}


	//04. 쿼리메소드를 이용하여 AND 혹은 OR 조건 처리
	// 속성이 두개 이상일 때는 파라미터 역시 지정한 속성의 수만큼 맞춰줘야 한다.
	// 예를 들어, 게시글의 title과 content 컬럼에 특정한 문자열이 들어있는 게시물을 검색한다면
	// findBy+TitleContaining+OR+ContentContaining 과 같은 형태가 된다.
	// OR 조건의 처리 -> '%' + 키워드 + '%'	Containing
//	@Test
//	public void testByAndOr() {
//		Collection<Board> results = boardRepo.findByTitleContainingOrContentContaining("작성자00", "0 채우기");
//		results.forEach(board -> System.out.println(board));
//	}

	//05. 쿼리메소드를 이용하여 부등호처리
	// 부등호 처리 -> title LIKE % ? % AND BNO > ?
	// title 이 5이면서 BNO가 50이상인
	// GreaterThan : >
	// LessThan : <
//	@Test
//	public void testByGreaterThan() {
//		Collection<Board> results = boardRepo.findByTitleContainingAndBnoGreaterThan("5", 50L);
//		results.forEach(board -> System.out.println(board));
//	}

	//06. 쿼리메소드를 이용한 order by 처리
	// OrderBy+컬럼명+Asc or Desc
	// bno가 ? 보다 큰 게시물을 bno 값의 내림차순으로 정렬
//	@Test
//	public void testBnoOrderBy() {
//		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(90L);
//		results.forEach(board -> System.out.println(board));
//	}


	//07. 페이징 처리와 정렬
	/**
	 * 페이징 처리와 정렬
	 * bno > ? ORDER BY bno DESC limit ?, ?
	 * 기존 코드와 동일하지만 파라메터에 Pageable이 적용되어있고,
	 * 리턴 타입으로 Collection이 아닌 List<>로 바뀌었다.
	 * Pageable 인터페이스가 적용되는 경우,
	 * 리턴 타입은 org.springframework.data.domain.Slice 타입
	 * o.sfw.data.domain.Page 타입
	 * java.util.List 타입을 이용해야 한다.
	 */
	//select * from tbl_boards where bno > 0 order by bno desc limit 0,10;
//	@Test
//	public void testBnoOrderByPaging() {
//		Pageable paging = new PageRequest(0,10);
//
//		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
//		results.forEach(board -> System.out.println(board));
//	}

	//08. 생성자 PageRequest를 이용하여 정렬방향과, 정렬칼럼을 지정)
	/**
	 * PageRequest(int page, int size)
	 * PageRequest(int page, Sort.Direction deriction, String... props)
	 * PageRequest(int page, int size, Sort sort)
	 *
	 * 쿼리메소드에서 OrderBy 구분 없이 Pageable만을 파라미터로 처리할 수 있다.
	 * select * from tbl_boards where bno > ? order by bno asc limit 0, 10
	 */
//	@Test
//	public void testBnoPagingSort() {
//		Pageable paging = new PageRequest(0, 10, Sort.Direction.ASC, "bno");
//
//		Collection<Board> results = boardRepo.findByBnoGreaterThan(0L, paging);
//		results.forEach(board -> System.out.println(board));
//
//	}

	//09. 08소스를 다음과 같이 변경..
	/**
	 * Page<Board> 는 아래와 같음 메소드들이 있다.
	 *
	 *
	 *  int getNumber() 			: 현재 페이지의 정보
	 *  int getSize()				: 한 페이지의 크기
	 *  int getTotalPages()			: 전체 페이지의 수
	 *  int getNumberOfElements()	: 결과 데이터 수
	 *  boolean hasPreviousPage() 	: 이전 페이지의 존재 여부
	 *  boolean hasNextPage()		: 다음 페이지의 존재 여부
	 *  boolean isLastPage()		: 마지막 페이지 여부
	 *  Pageable nextPageable()		: 다음 페이지 객체
	 *  Pageable previousPageable()	: 이전 페이지 객체
	 *  List<T> getContent()		: 조회된 데이터
	 *  boolean hasContent() 		: 조회된 데이터
	 *  boolean hasContent() 		: 결과 존재 여부
	 *  Sort getSort() 				: 검색 시 사용된 Sort 정보
	 *
	 */
//	Hibernate: select bno ,content, regdate, title, updatedate, writer from tbl_boards where bno > ? order by bno asc limit ?
//	Hibernate: select count(bno) from tbl_boards where bno > ?
//	아래 소스를 실행하면 이전과는 다르게 SQL문이 두번 실행되는 부분이 특이점이다.

	@Test
	public void testBnoPagingSort2() {
		Pageable paging = new PageRequest(0, 10, Sort.Direction.ASC, "bno");

		Page<Board> result = boardRepo.findByBnoGreaterThan(0L, paging);

		System.out.println("PAGE SIZE[int]: "+ result.getSize());
		System.out.println("TOTAL PAGES[int]: "+ result.getTotalPages());
		System.out.println("TOTAL COUNT[long]: "+ result.getTotalElements());
		System.out.println("NEXT[Pageable]: "+ result.nextPageable());

		List<Board> list = result.getContent();

		list.forEach(board -> System.out.println(board));
	}




}

/*
 * 쿼리 메소드
 *
 * find..By...
 * read..By...
 * query..By...
 * get..By...
 * count..By...
 *
 *	예를 들면 findBoardByTitle 이라고하면
 * 	Board 테이블을 제목으로 검색...
 *
 * 쿼리 메소드 리턴 타입은 Page<T>, Slice<T>, List<T> 와 같은
 * Collection<T> 형태가 될 수 있다.
 *
 * 가장 많이 사용되는 것은 List<T>, Page<T> 타입이다.
 *
 * BoardRepository.java 파일에
 *	public List<Board> findBoardByTitle(String title);
 *
 *like 구문 처리
 *	findBy와 더불어 가장 많이 사용하는 구문이 like 구문이다.
 *	like 쿼리 메소드 형태는 4가지가 있다.
 *
 *	형태				쿼리 메소드
 *단순like				Like
 *키워드 + '%'			StartingWith
 *'%' + 키워드			EndingWith
 *'%' + 키워드 + '%'	Containing
 *
 */
