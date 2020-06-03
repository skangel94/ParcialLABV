package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projections.CallsByMonth;
import edu.utn.utnphones.projections.PriceLastCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CallDao extends JpaRepository<Call,Integer> {

    @Procedure(value = "sp_insertcall")
    void addCall(String lineFrom, String lineTo, int seg, Date date);

    @Query(value = "select * from calls c inner join phone_lines pl on c.call_line_id_from = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id \n" +
            "where c.call_date >= ?1  and c.call_date <= ?2 and u.user_id = ?3",nativeQuery = true)
    List<Call> getCallsByDate(Date dateFrom, Date dateTo, int userId);

    @Query(value = "select * from calls c inner join phone_lines pl on c.call_line_id_from = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id \n" +
            "where c.call_minute_price > ?1 and u.user_id = ?2", nativeQuery = true)
    List<Call> getCallsByPrice(float price, int userId);


    @Query(value = "select u.user_name as name , u.user_dni as dni,(c.call_minute_price * (c.call_duration_seg/60)) as price from calls c inner join phone_lines pl on c.call_line_id_from = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id\n" +
            "where u.user_id = ?1\n" +
            "order by c.call_id desc\n" +
            "limit 1", nativeQuery = true)
    PriceLastCall getPriceLastCall(int userId);

    @Query(value = "select u.user_name as name, u.user_lastname  as lastname, SUM(c.call_duration_seg) as duration from calls c inner join phone_lines pl on c.call_line_id_from = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id\n" +
            "where month(c.call_date) = ?1 and u.user_id = pl.line_user_id\n" +
            "group by u.user_name ",nativeQuery = true)
    List<CallsByMonth> getCallsByMonth(int month);

    @Query(value = "select * from calls c inner join phone_lines pl on c.call_line_id_from = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id \n" +
            "where c.call_duration_seg = ?1 and u.user_id = pl.line_user_id",nativeQuery = true)
    List<Call> getCallsByDuration(int duration);

}
