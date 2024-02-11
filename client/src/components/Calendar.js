import classNames from 'classnames';
import { useEffect, useState } from 'react';

export default function Calendar() {
  const today = new Date();
  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth());
  const [calendarDays, setCalendarDays] = useState([]);

  function generateCalendarDays(year, month) {
    const firstDayOfMonth = new Date(year, month, 1);
    const firstDayOfWeek = firstDayOfMonth.getDay();
    const lastDayOfPrevMonth = new Date(year, month, 0);
    const daysInPrevMonth = lastDayOfPrevMonth.getDate();
    const lastDayOfMonth = new Date(year, month + 1, 0);
    const lastDayOfWeek = lastDayOfMonth.getDay();

    const days = [];
    let prevMonthDaysCount = firstDayOfWeek === 0 ? 6 : firstDayOfWeek - 1;

    for (let i = daysInPrevMonth - prevMonthDaysCount; i <= daysInPrevMonth; i++) {
      days.push({ day: i, isCurrentMonth: false });
    }

    for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
      days.push({ day: i, isCurrentMonth: true });
    }

    const nextMonthDaysCount = lastDayOfWeek === 6 ? 0 : 6 - lastDayOfWeek;
    for (let i = 1; i <= nextMonthDaysCount; i++) {
      days.push({ day: i, isCurrentMonth: false });
    }

    setCalendarDays(days);
  }

  useEffect(() => {
    generateCalendarDays(year, month);
  }, [year, month]);

  function handleCalendarPrev() {
    if (month === 0) {
      setYear(year - 1);
      setMonth(11);
    } else {
      setMonth(month - 1);
    }
  }

  function handleCalendarNext() {
    if (month === 11) {
      setYear(year + 1);
      setMonth(0);
    } else {
      setMonth(month + 1);
    }
  }

  return (
    <div className="calendar">
      <div className="calendar_header">
        <div onClick={handleCalendarPrev} className="header_button">
          &lt;
        </div>
        <div id="year-month" className="year-month">{`${year}년 ${month + 1}월`}</div>
        <div onClick={handleCalendarNext} className="header_button">
          &gt;
        </div>
      </div>
      <div className="calendar_grid">
        <div className="calendar_line">
          <div className="calendar_day">일</div>
          <div className="calendar_day">월</div>
          <div className="calendar_day">화</div>
          <div className="calendar_day">수</div>
          <div className="calendar_day">목</div>
          <div className="calendar_day">금</div>
          <div className="calendar_day">토</div>
        </div>
        {calendarDays.map((day, index) => {
          if (index % 7 === 0) {
            return (
              <div key={index} className="calendar_line dates">
                {calendarDays.slice(index, index + 7).map((d, idx) => (
                  <div
                    key={idx}
                    className={classNames(
                      'calendar_day',
                      { 'current-month': d.isCurrentMonth },
                      { 'other-month': !d.isCurrentMonth },
                      { weekend: idx === 0 || idx === 6 },
                    )}
                  >
                    {d.day}
                  </div>
                ))}
              </div>
            );
          }
          return null;
        })}
      </div>
    </div>
  );
}
