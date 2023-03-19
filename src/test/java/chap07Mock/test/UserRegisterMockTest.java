package chap07Mock.test;

import chap07.exception.DupldException;
import chap07Mock.EmailNotifier;
import chap07Mock.UserRegister;
import chap07Mock.WeakPasswordChecker;
import chap07Mock.entity.User;
import chap07Mock.exception.WeakPasswordException;
import chap07Mock.fake.MemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRegisterMockTest {

    private UserRegister userRegister;
    private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);
    private final MemoryUserRepository fakeRepository = new MemoryUserRepository(); //가짜
//    private final StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker(); //스텁
//    private final SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier(); //스파이

    @BeforeEach
    void setUp(){
//        userRegister = new UserRegister(stubWeakPasswordChecker); //스텁
        userRegister = new UserRegister(mockPasswordChecker, fakeRepository, mockEmailNotifier); //스텁
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void register (){
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw"))
                        .willReturn(true); // 행동 설정 => 이떄의 mockPasswordChecker는 true 반환.
//        stubWeakPasswordChecker.setWeak(true); //실제 메서드(스텁)를 통해 weak라는 결과값을 받기 않고
        assertThrows(WeakPasswordException.class, () -> {
            userRegister.register("id","pw","email"); //rk
        });
    }

    @DisplayName("회원가입시 암호검사 수행 여부 검증")
    @Test
    void checkPassword (){
        userRegister.register("id","pw","email");

        BDDMockito.then(mockPasswordChecker) //인자로 전달한 모의 객체의
                .should()   //특정 메서드가 호출됐는지 검증한다.
                .checkPasswordWeak(BDDMockito.anyString()); //임의의 String type parameter를 이용해 호출 여부 확인
    }

    @DisplayName("동일한 아이디를 가진 회원 존재하면 가입 실패")
    @Test
    public void duplicate_id_test(){
        //상황: 동일 아이디를 가진 회원 존재 repository 
        fakeRepository.save(new User("id","pw1","email@emial.com")); //가짜
        //실행 결과 검증 => exception 발생
        assertThrows(DupldException.class, () -> {
            userRegister.register("id","pw","email");
        });
    }

    @DisplayName("회원가입성공")
    @Test
    public void register_unique_id_success_test(){
        userRegister.register("id","pw","email"); //가짜
        //실행 결과 검증 => assertEquals로 가입된 객체를 확인
        User savedUser = fakeRepository.findById("id"); //가입된 객체를 가져온다.
        assertEquals("id", savedUser.getId());
        assertEquals("email", savedUser.getEmail());

    }
    @Test
    @DisplayName("회원가입 시 이메일 전송 확인 ")
    public void notifyTest(){
        //전제 조건: 회원가입 완료
        userRegister.register("id","pw","email@email.com");
        //발송이 됐는지, 그리고 발송된 이메일이 회원가입시 입력한 이메일과 일치하는지
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class); //메서드를 호출할 때 전달한 객체를 담는 기능
        BDDMockito.then(mockEmailNotifier)
                        .should()//메서드가 호출됐는지
                                .sendRegisterEmail(captor.capture()); //captor에 담긴다.
        String realEmail = captor.getValue(); //captor의 값을 확인

        assertEquals("email@email.com", realEmail);
    }
}
