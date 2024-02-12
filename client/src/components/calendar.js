import { useState } from 'react';
import classNames from 'classnames';

export default function Calendar({ year, month }) {
  const [activeAtom, setActiveAtom] = useState(null);
  const [currentYear, setCurrentYear] = useState(year);
  const [currentMonth, setCurrentMonth] = useState(month);

  const getLastDayOfMonth = (year, month) => {
    return new Date(year, month, 0).getDate();
  };

  const getAllDates = (year, month) => {
    const lastDayOfMonth = getLastDayOfMonth(year, month);
    const allDates = [];

    for (let i = 1; i <= lastDayOfMonth; i++) {
      allDates.push(i);
    }

    return allDates;
  };

  const allDates = getAllDates(currentYear, currentMonth);

  const renderWeeks = () => {
    const weeks = [];

    const firstDayOfMonth = new Date(currentYear, currentMonth - 1, 1);
    const firstDayOfWeek = firstDayOfMonth.getDay();

    for (let i = -firstDayOfWeek; i < allDates.length; i += 7) {
      let weekDates = allDates.slice(Math.max(i, 0), i + 7);
      const firstNullCount = 0 - i;
      if (firstNullCount > 0) {
        const nullArray = Array(firstNullCount).fill(null);
        weekDates = [...nullArray, ...weekDates];
      }
      if (weekDates.length < 7) {
        const nullArray = Array(7 - weekDates.length).fill(null);
        weekDates = [...weekDates, ...nullArray];
      }

      weeks.push(
        <div key={i} className={classNames('week')}>
          {weekDates.map((date, index) => {
            return (
              <div
                key={index}
                className={classNames({
                  'atom-nothing': date == null,
                  'atom': date != null && index > 0 && index < 6,
                  'atom-weekday': date != null && index === 0 || index === 6,
                  'active': date != null && date === activeAtom
                })}
                onClick={() => handleClickAtom(date)}
              >
                <span className={classNames('text')}>{date}</span>
              </div>
            );
          })}
        </div>
      );
    }

    return weeks;
  };

  const handleClickAtom = (date) => {
    setActiveAtom(date);
  };

  const handleLastMonth = () => {
    setActiveAtom(null);
    if (currentMonth === 1) {
      setCurrentMonth(12);
      setCurrentYear(currentYear - 1); // 이전 년도로 설정
    } else {
      setCurrentMonth(currentMonth - 1);
    }
  };

  const handleNextMonth = () => {
    setActiveAtom(null);
    if (currentMonth === 12) {
      setCurrentMonth(1);
      setCurrentYear(currentYear + 1); // 다음 년도로 설정
    } else {
      setCurrentMonth(currentMonth + 1);
    }
  };

  return (
    <div className={classNames('calendar')}>
      <div className={classNames('month')}>
        <div className={classNames('arrow-left')} onClick={handleLastMonth}>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="20" viewBox="0 0 12 20" fill="none">
            <path d="M10.5 1.25L1.82654 10.1548L10.5 18.75" stroke="#DDD8D2" strokeWidth="2"/>
          </svg>
        </div>
        <span className={classNames('text')}>{currentYear}년 {currentMonth}월</span>
        <div className={classNames('arrow-right')} onClick={handleNextMonth}>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="20" viewBox="0 0 12 20" fill="none">
            <path d="M1.5 18.75L10.1735 9.84524L1.5 1.25" stroke="#DDD8D2" strokeWidth="2"/>
          </svg>
        </div>
      </div>
      <div className={classNames('calendar-content')}>
        <div className={classNames('week-title')}>
          <div className={classNames('atom-weekday')}>
            <span className={classNames('text')}>일</span>
          </div>
          <div className={classNames('atom')}>
            <span className={classNames('text')}>월</span>
          </div>
          <div className={classNames('atom')}>
            <span className={classNames('text')}>화</span>
          </div>
          <div className={classNames('atom')}>
            <span className={classNames('text')}>수</span>
          </div>
          <div className={classNames('atom')}>
            <span className={classNames('text')}>목</span>
          </div>
          <div className={classNames('atom')}>
            <span className={classNames('text')}>금</span>
          </div>
          <div className={classNames('atom-weekday')}>
            <span className={classNames('text')}>토</span>
          </div>
        </div>
        {renderWeeks()}
      </div>
    </div>
  );
}