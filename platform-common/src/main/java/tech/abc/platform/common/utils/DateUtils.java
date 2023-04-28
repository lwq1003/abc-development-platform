package tech.abc.platform.common.utils;

/**
 * 日期工具类
 *
 * @author wqliu
 * @date 2023-03-08
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    // /**
    //  * 转成字符串，本方法不做时区处理，原文格式化输出
    //  *
    //  * @param localDate
    //  * @return yyyy-MM-dd
    //  */
    //    public static String format(LocalDate localDate) {
    //    	Assert.notNull(localDate, "参数localDate不能为空");
    //        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    //    }
    //
    //    /**
    //     * 获取系统默认时间在某时区对应的时间。如北京时间2019-09-10，返回时区Pacific/Honolulu的时间可能为2019-09-09
    //     *
    //     * @param localDate 被格式的时间（系统默认时区）
    //     * @param zoneId 预期时间所在时区
    //     * @return  固定格式 yyyy-MM-dd
    //     */
    //    public static String format(LocalDate localDate, ZoneId zoneId) {
    //    	Assert.notNull(localDate, "参数localDate不能为空");
    //    	Assert.notNull(zoneId, "参数zoneId不能为空");
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(zoneId);
    //        return ZonedDateTime.of(localDate.atTime(0,0), ZoneId.systemDefault()).format(formatter);
    //    }
    //
    //    /**
    //     * 转成字符串，本方法不做时区处理，原文格式化输出
    //     *
    //     * @param localDateTime
    //     * @return yyyy-MM-dd HH:mm:ss
    //     */
    //    public static String format(LocalDateTime localDateTime) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    //    }
    //
    //    /**
    //     *  获取系统默认时间在某时区对应的时间。如北京时间2019-09-10 10:53:26，返回时区Pacific/Honolulu的时间则为2019-09-09 16:53:26
    //     *
    //     * @param localDateTime 被格式的时间（系统默认时区）
    //     * @param zoneId 预期时间所在时区
    //     * @return 固定格式 yyyy-MM-dd HH:mm:ss
    //     */
    //    public static String format(LocalDateTime localDateTime, ZoneId zoneId) {
    //        return format(localDateTime, zoneId, "yyyy-MM-dd HH:mm:ss");
    //    }
    //
    //    /**
    //     *  获取系统默认时间在某时区对应的时间。如北京时间2019-09-10 10:53:26，返回时区Pacific/Honolulu的时间则为2019-09-09 16:53:26
    //     *
    //     * @param localDateTime 被格式的时间（系统默认时区）
    //     * @param zoneId 预期时间所在时区
    //     * @param pattern 输出字符串的格式
    //     * @return 固定格式 yyyy-MM-dd HH:mm:ss
    //     */
    //    public static String format(LocalDateTime localDateTime, ZoneId zoneId, String pattern) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //    	Assert.notNull(zoneId, "参数zoneId不能为空");
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
    //        return formatter.format(localDateTime.atZone(ZoneId.systemDefault()));
    //    }
    //
    //    /**
    //     * LocalDateTime是不含时区的某时区时间，本方法默认localDateTime为UTC时间，把此时间在zoneId时区下格式化为字符串
    //     *
    //     * @param localDateTime UTC 时间
    //     * @param zoneId  目标时区
    //     * @param pattern
    //     * @return
    //     */
    //    public static String formatUtc(LocalDateTime localDateTime, ZoneId zoneId, String pattern) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //    	Assert.notNull(zoneId, "参数zoneId不能为空");
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
    //        return formatter.format(localDateTime.atZone(ZoneOffset.UTC));
    //    }
    //
    //    /**
    //     * 转成字符串
    //     *
    //     * @param zonedDateTime
    //     * @return yyyy-MM-dd HH:mm:ss
    //     */
    //    public static String format(ZonedDateTime zonedDateTime) {
    //    	Assert.notNull(zonedDateTime, "参数zonedDateTime不能为空");
    //        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    //    }
    //
    //    /**
    //     *  获取某ZonedDateTime时间在ZoneId所对应的时刻。如北京时间2019-09-10 11:08:36对于时区America/Los_Angeles时间为2019-09-09 20:08:36
    //     *
    //     * @param zonedDateTime 被转换的时间对象
    //     * @param zoneId 目标时区
    //     * @return yyyy-MM-dd HH:mm:ss
    //     */
    //    public static String format(ZonedDateTime zonedDateTime, ZoneId zoneId) {
    //    	Assert.notNull(zonedDateTime, "参数zonedDateTime不能为空");
    //    	Assert.notNull(zoneId, "参数zoneId不能为空");
    //        return zonedDateTime.withZoneSameInstant(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    //    }
    //
    // /**
    //  * LocalTime / LocalDate / LocalDateTime / ZonedDateTime ==> String
    //     * <p>
    //  * 注意：此方法不做时区处理，原文格式化输出，如果时间为UTC时，请慎用此方法
    //     * <p>
    //  * 具体格式详见：{@link DateTimeFormatter}
    //  *
    //  */
    // public static String format(Temporal temporal, String pattern) {
    // 	Assert.notNull(temporal, "参数temporal不能为空");
    // 	Assert.hasText(pattern, "参数pattern不能为空");
    // 	return DateTimeFormatter.ofPattern(pattern).format(temporal);
    // }
    //
    //    /**
    //     *
    //     * @param dateStr yyyy-MM-dd
    //     * @return
    //     */
    //    public static LocalDate toLocalDate(String dateStr) {
    //    	Assert.hasText(dateStr, "参数dateStr不能为空");
    //        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    //    }
    //
    //    /**
    //     * 具体格式详见：{@link DateTimeFormatter}
    //     *
    //     * @param dateStr
    //     * @param pattern
    //     * @return
    //     */
    //    public static LocalDate toLocalDate(String dateStr, String pattern) {
    //    	Assert.hasText(dateStr, "参数dateStr不能为空");
    //    	Assert.hasText(pattern, "参数pattern不能为空");
    //        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    //    }
    //
    //    /**
    //     *
    //     * @param dateTimeStr yyyy-MM-dd HH:mm:ss
    //     * @return
    //     */
    //    public static LocalDateTime toLocalDateTime(String dateTimeStr) {
    //    	Assert.hasText(dateTimeStr, "参数dateTimeStr不能为空");
    //        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    //    }
    //
    //    /**
    //     * 具体格式详见：{@link DateTimeFormatter}
    //     *
    //     * @param dateTimeStr
    //     * @param pattern
    //     * @return
    //     */
    //    public static LocalDateTime toLocalDateTime(String dateTimeStr, String pattern) {
    //    	Assert.hasText(dateTimeStr, "参数dateTimeStr不能为空");
    //    	Assert.hasText(pattern, "参数pattern不能为空");
    //        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    //    }
    //
    //    /**
    //     * 关于支持的格式同{@link ZonedDateTime#parse(CharSequence)}
    //     *
    //     * @param zonedDateTimeStr
    //     * @return
    //     */
    //    public static ZonedDateTime toZonedDateTime(String zonedDateTimeStr) {
    //    	Assert.hasText(zonedDateTimeStr, "参数zonedDateTimeStr不能为空");
    //        return ZonedDateTime.parse(zonedDateTimeStr);
    //    }
    //
    //    /**
    //     * 把zonedDateTimeStr转成zoneId对应的时间，
    //     * <pre>
    //     * 若：
    //     *   zonedDateTimeStr：2019-09-09 09:53:08.662 +0800
    //     *   pattern:yyyy-MM-dd HH:mm:ss.SSS Z
    //     *   zoneId:Europe/Budapest
    //     * 则返回值为 2019-09-09T03:53:08.662+02:00[Europe/Budapest]
    //     * </pre>
    //     *
    //     * @param zonedDateTimeStr
    //     * @param zoneId
    //     * @return
    //     */
    //    public static ZonedDateTime toZonedDateTime(String zonedDateTimeStr, ZoneId zoneId) {
    //    	Assert.hasText(zonedDateTimeStr, "参数zonedDateTimeStr不能为空");
    //    	Assert.notNull(zoneId, "参数zoneId不能为空");
    //        return ZonedDateTime.parse(zonedDateTimeStr).withZoneSameInstant(zoneId);
    //    }
    //
    //    /**
    //     * Date转LocalDate，是将Date对象所在时区和时间合并后转为LocalDate，这里的“Local”不是只系统本地，而是Date所在时区的本地
    //     *<p>
    //     *     如北京时间2019-09-10 11:27
    //     * @param date
    //     * @return 返回于参数在同一时刻的LocalDate对象，如参数描述2019-09-08 00:35:00.000 +0800，则返回值为2019-09-08的对象
    //     */
    //    public static LocalDate toLocalDate(Date date) {
    //    	Assert.notNull(date, "参数date不能为空");
    //        return toZonedDateTime(date).toLocalDate();
    //    }
    //
    //    /**
    //     * Date转LocalDateTime，是将Date对象所在时区和时间合并后转为LocalDateTime，这里的“Local”不是只系统本地，而是Date所在时区的本地
    //     *
    //     * @param date
    //     * @return 返回于参数在同一时刻的LocalDateTime对象，如参数描述2019-09-08 00:35:00.000 +0800，则返回值为2019-09-08 00:35:00.000的对象
    //     */
    //    public static LocalDateTime toLocalDateTime(Date date) {
    //    	Assert.notNull(date, "参数date不能为空");
    //        return toZonedDateTime(date).toLocalDateTime();
    //    }
    //
    //    /**
    //     * {@link Date} 没有时区概念，所以此方法返回date所对应的UTC时间在当前系统默认时区下的时刻。
    //     * <p>
    //     * 注意：这里有个违反直觉的地方，通过带时区解析过来的date对象，看起来会转为和date所带时区相同的ZonedDateTime对象，但事实不是这样的。
    //     * 如当前系统北京时间2019-09-10 12:32:25.011 +0800，把系统默认时区设置为America/Los_Angeles，则返回的时间为2019-09-09 21:32:25.011 -0700。
    //     * 所以本方法直接使用了date对应的UTC时间+系统默认时区的方式做的类型转换。
    //     * </p>
    //     * <pre>
    //     *     可通过如下代码验证：
    //     *     Date date = DateUtils.parseDate("2019-09-09 21:32:25.011 -0700", "yyyy-MM-dd HH:mm:ss.SSS Z");
    // *         println(DateUtils.toZonedDateTime(date));
    // *         TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
    // *         println(DateUtils.toZonedDateTime(date));
    //     * </pre>
    //     *
    //     * @param date
    //     * @return
    //     */
    //    public static ZonedDateTime toZonedDateTime(Date date) {
    //    	Assert.notNull(date, "参数date不能为空");
    //        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    //    }
    //
    //    /**
    //     * 获取date描述的UTC时间在时区zonedId的时刻
    //     *
    //     * @param date
    //     * @param zoneId
    //     * @return
    //     */
    //    public static ZonedDateTime toZonedDateTime(Date date, ZoneId zoneId) {
    //    	Assert.notNull(date, "参数date不能为空");
    //        return ZonedDateTime.ofInstant(date.toInstant(), zoneId);
    //    }
    //
    //    /**
    //     * 带时区的时间类ZonedDateTime转为不带时区的Date对象，转换后的Date对象是ZonedDateTime对于的UTC时间在系统默认时区的时刻。
    //     * 注意：有点可能违背直觉的地方，如通过解析2019-09-09T21:55:30.737-07:00获取的ZonedDateTime对象，当默认时区为Asia/Shanghai时，转换后的date值为2019-09-10 12:55:30.737 +0800。
    //     * <pre>
    //     * 即使通过如下方式获取date对象，依然会丢失ZonedDateTime的时区信息
    //     *     String pattern = "yyyy-MM-dd HH:mm:ss.SSS Z";
    //     *     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    //     *     DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    //     *     sdf.parse(dateTimeFormatter.format(zonedDateTime));
    //     * </pre>
    //     *
    //     * @param zonedDateTime
    //     * @return 转换失败时返回null
    //     */
    //    public static Date toDate(ZonedDateTime zonedDateTime) {
    //    	Assert.notNull(zonedDateTime, "参数zonedDateTime不能为空");
    //        return Date.from(zonedDateTime.toInstant());
    //    }
    //
    //    /**
    //     * 转成时分秒毫秒都为0的Date对象，
    //     * ${@link LocalDate}没有时区信息，所以这里存在违反直觉的地方，即若localDate是通过其他办法获取的其他时区的日期时，
    //     * 转换后日期描述值没有改变，但时区可能不一致了。
    //     * 比如获取通过LocalDate.now(ZoneId.of("America/Los_Angeles"))日期为2019-09-09，转换后则为2019-09-09 00:00:00.000 +0800（变成了北京时间）
    //     * 本方法默认localDate时区为系统默认时区，即默认localDate描述系统当前默认时区的时间
    //     * <p>
    //     * 注意：本方法返回的对象要慎重用于日期计算，仅建议用于不带时区的日期值获取。
    //     *
    //     * @param localDate
    //     * @return
    //     */
    //    public static Date toDate(LocalDate localDate) {
    //    	Assert.notNull(localDate, "参数localDate不能为空");
    //        return toDate(localDate.atStartOfDay().atZone(ZoneId.systemDefault()));
    //    }
    //
    //    /**
    //     * 此方法有问题，不删除，是为了告诉使用者或准备提供此种方法的人不能这样操作。
    //     *
    //     * @param localDate
    //     * @param zoneId
    //     * @return
    //     */
    //    @Deprecated
    //    public static Date toDate(LocalDate localDate, ZoneId zoneId) {
    //        throw new UnsupportedOperationException("无法保证LocalDate到Date类型的正确转换，因为时区是以小时计的，若时区偏离不足24小时，则无法正确获取");
    //    }
    //
    //    /**
    //     * LocalDateTime ==> Date，本方法会默认localDateTime描述的时间为系统默认时区的。
    //     * 注意：若此localDateTime是提供其他方法获取的其他时区的时间，本方法返回的对象要慎重用于日期计算，仅建议用于不带时区的日期值获取。
    //     *
    //     * @param localDateTime
    //     * @return
    //     */
    //    public static Date toDate(LocalDateTime localDateTime) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //        return toDate(localDateTime.atZone(ZoneId.systemDefault()));
    //    }
    //
    //    /**
    //     *
    //     *  这里是zoneId只是用于说明localDate所表示的时间属于哪个时区，与返回的date对象对于的时间没有直接关系。
    //     *  本方法返回的对象要慎重用于日期计算，仅建议用于不带时区的日期值获取。
    //     * @param localDateTime
    //     * @param zoneId
    //     * @return
    //     */
    //    public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //    	Assert.notNull(zoneId, "参数zoneId不能为空");
    //        return toDate(localDateTime.atZone(zoneId));
    //    }
    //
    //    /**
    //     * 指定日期的开始时间
    //     *
    //     * @param localDateTime
    //     * @return
    //     */
    //    public static LocalDateTime beginOfDay(LocalDateTime localDateTime) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    //    }
    //
    //    /**
    //     * 指定日期的结束时间
    //     *
    //     * @param localDateTime
    //     * @return
    //     */
    //    public static LocalDateTime endOfDay(LocalDateTime localDateTime) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    //    }
    //
    //    /**
    //     * 指定日期的开始时间
    //     *
    //     * @param date
    //     * @return
    //     */
    //    public static LocalDateTime beginOfDay(Date date) {
    //    	Assert.notNull(date, "参数date不能为空");
    //        return LocalDateTime.of(toLocalDateTime(date).toLocalDate(), LocalTime.MIN);
    //    }
    //
    //    /**
    //     * 指定日期的结束时间
    //     *
    //     * @param date
    //     * @return
    //     */
    //    public static LocalDateTime endOfDay(Date date) {
    //    	Assert.notNull(date, "参数date不能为空");
    //        return LocalDateTime.of(toLocalDateTime(date).toLocalDate(), LocalTime.MAX);
    //    }
    //
    //    /**
    //     * 详见 {@link Temporal#plus(long, TemporalUnit)}
    //     *
    //     * @param temporal         时间对象
    //     * @param amountToSubtract 增加的数值（可为负数）
    //     * @param unit             时间单位，如天：ChronoUnit.DAYS
    //     * @param <T>
    //     * @return
    //     */
    //    public static <T> T plus(Temporal temporal, long amountToSubtract, TemporalUnit unit) {
    //    	Assert.notNull(temporal, "参数temporal不能为空");
    //    	Assert.isTrue(amountToSubtract != 0, "参数amountToSubtract为0时此操作无意义");
    //    	Assert.notNull(unit, "参数unit不能为空");
    //        return (T) temporal.plus(amountToSubtract, unit);
    //    }
    //
    //    /**
    //     * 获取指定日期所在星期的星期一0点
    //     *
    //     * @param localDate
    //     * @return
    //     */
    //    public static LocalDateTime startOfWeek(LocalDate localDate) {
    //    	Assert.notNull(localDate, "参数localDate不能为空");
    //    	return localDate.with(DayOfWeek.MONDAY).atStartOfDay();
    //    }
    //
    //    /**
    //     * 获取指定日期所在星期的星期一0点
    //     *
    //     * @param localDateTime
    //     * @return
    //     */
    //    public static LocalDateTime startOfWeek(LocalDateTime localDateTime) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //    	return localDateTime.toLocalDate().with(DayOfWeek.MONDAY).atStartOfDay();
    //    }
    //
    //    /**
    //     * 获取指定日期所在月份的第一天0点
    //     *
    //     * @param localDate
    //     * @return
    //     */
    //    public static LocalDateTime startOfMonth(LocalDate localDate) {
    //    	Assert.notNull(localDate, "参数localDate不能为空");
    //    	return localDate.withDayOfMonth(1).atStartOfDay();
    //    }
    //
    //    /**
    //     * 获取指定日期所在月份的第一天0点
    //     *
    //     * @param localDateTime
    //     * @return
    //     */
    //    public static LocalDateTime startOfMonth(LocalDateTime localDateTime) {
    //    	Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //    	return localDateTime.toLocalDate().withDayOfMonth(1).atStartOfDay();
    //    }
    //
    //    /**
    //     * 返回UTC的当前时间
    //     *
    //     * @return
    //     */
    //    public static LocalDateTime getLocalDateTimeNowUtc() {
    //        return LocalDateTime.now(ZoneOffset.UTC);
    //    }
    //
    //
    //    /**
    //     *  获取当前日期对应UTC的开始时间，如北京时间2019-09-29全天对应的UTC开始时间为2019-09-28 16:00
    //     *
    //     * @param localDateTime 系统默认时区的时间
    //     * @return
    //     */
    //    public static LocalDateTime beginOfDayUtc(LocalDateTime localDateTime) {
    //        Assert.notNull(localDateTime, "参数localDateTime不能为空");
    //        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN)
    //                .atZone(ZoneId.systemDefault())
    //                .withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    //    }

}
