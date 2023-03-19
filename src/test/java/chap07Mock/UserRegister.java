package chap07Mock;

import chap07.exception.DupldException;
import chap07Mock.entity.User;
import chap07Mock.exception.WeakPasswordException;

public class UserRegister {
    private final WeakPasswordChecker passwordChecker; //스텁 사용됨.
    private final UserRepository repository;
    private final EmailNotifier emailNotifier;
    public UserRegister(WeakPasswordChecker weakPasswordChecker, UserRepository repository, EmailNotifier emailNotifier) { //스텁으로 만들 수 있는 생성자.
        this.passwordChecker = weakPasswordChecker;
        this.repository = repository;
        this.emailNotifier = emailNotifier;
    }

    public void register(String id, String pw, String email) {
        if (passwordChecker.checkPasswordWeak(pw)){
            throw new WeakPasswordException();
        }
        //이미 저장소에 저장되어 있다면 에러발생
        User user = repository.findById(id);
        if (user != null){
            throw new DupldException();
        }
        repository.save(new User(id, pw, email));
        emailNotifier.sendRegisterEmail(email);

    }
}
