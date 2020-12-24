import com.github.mybatis.monitor.dao.TUserMapper;
import com.github.mybatis.monitor.entity.TUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class TUserMapperTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1.读取mybatis配置文件创SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    @Test
    public void selectByPrimaryKey() {
        // 2.获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.获取对应mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
        // 4.执行查询语句并返回结果
        System.out.println(mapper.selectUserHealthReport());
    }

    @Test
    public void insertSomeData(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
        List<TUser> tUsers = produceTUserData(10);
        for (TUser tUser : tUsers) {
            int i = mapper.insert1(tUser);
        }
        sqlSession.commit();
    }

    private List<TUser> produceTUserData(Integer times){
        Random random = new Random();
        List<TUser> tUsers = new ArrayList<TUser>();
        for (int i = 0; i <times ; i++) {
            TUser tUser = new TUser();
            tUser.setEmail("admin@live.com"+random.nextInt());
            tUser.setMobile(String.valueOf(random.nextInt()));
            tUser.setNote(String.valueOf(random.nextGaussian()));
            tUser.setRealName("wangNoWord"+random.nextInt());
            tUser.setUserName("simon"+random.nextInt());
            tUser.setPositionId(random.nextInt());
            tUser.setSex((byte) 0);
            tUsers.add(tUser);
        }
        return tUsers;
    }
}