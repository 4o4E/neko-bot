package top.e404.bot

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.datasource.DataSourceFactory
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import top.e404.bot.pojo.HistoryData
import java.util.*
import javax.sql.DataSource

private val sqlSessionFactory = HistoryData::class
    .java.classLoader
    .getResourceAsStream("mybatis-config.xml")!!
    .use { SqlSessionFactoryBuilder().build(it, "mysql")!! }

val session: SqlSession
    get() = sqlSessionFactory.openSession(true)!!

class HikariDataSourceFactory : DataSourceFactory {
    lateinit var props: Properties
    override fun setProperties(props: Properties) {
        this.props = props
    }

    private var dataSource: DataSource? = null
    override fun getDataSource() = dataSource
        ?: HikariDataSource(
            HikariConfig(props)
        ).also { dataSource = it }
}