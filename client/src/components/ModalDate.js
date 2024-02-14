import classNames from 'classnames';
import { BtnBlack } from './Button';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import Calendar from './Calendar';
import TimeSlots from './TimeSlots';

export default function ModalDate({ nextStep, props }) {

  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth();

  const [departureYear, setDepartureYear] = useState(props.departureYear);
  const [departureMonth, setDepartureMonth] = useState(props.departureMonth);
  const [departureDay, setDepartureDay] = useState(props.departureDay);
  const [departureTime, setDepartureTime] = useState(props.departureTime);

  const [pickupYear, setPickupYear] = useState(props.pickupYear);
  const [pickupMonth, setPickupMonth] = useState(props.pickupMonth);
  const [pickupDay, setPickupDay] = useState(props.pickupDay);
  const [pickupTime, setPickupTime] = useState(props.pickupTime);

  const [showTimes, setShowTimes] = useState(false);
  const [timeData, setTimeData] = useState(null);
  const [selectedTime, setSelectedTime] = useState(null);

  const [selectedInput, setSelectedInput] = useState('departure');
  
  const [calendarYear, setCalendarYear] = useState(currentYear);
  const [calendarMonth, setCalendarMonth] = useState(currentMonth);
  const [calendarDay, setCalendarDay] = useState(null);

  const handleNext = () => {
    if (departureTime === '' || pickupTime === '') return;
    nextStep(departureYear, departureMonth, departureDay, departureTime,
      pickupYear, pickupMonth, pickupDay, pickupTime);
  }

  const handleLastMonth = () => {
    setShowTimes(false);
    setCalendarDay(null);
    if (currentMonth === 0) {

      if (selectedInput === 'departure'
        && calendarYear - 1 === departureYear
        && 11 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear - 1 === pickupYear
        && 11 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(11);
      setCalendarYear(calendarYear - 1);
    } else {
      
      if (selectedInput === 'departure'
        && calendarYear === departureYear
        && calendarMonth - 1 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear === pickupYear
        && calendarMonth - 1 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(calendarMonth - 1);
    }
  };

  const handleNextMonth = () => {
    setShowTimes(false);
    setCalendarDay(null);

    if (currentMonth === 11) {

      if (selectedInput === 'departure'
        && calendarYear + 1 === departureYear
        && 0 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear + 1 === pickupYear
        && 0 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(0);
      setCalendarYear(calendarYear + 1);
    } else {

      if (selectedInput === 'departure'
        && calendarYear === departureYear
        && calendarMonth + 1 === departureMonth) {
        setCalendarDay(departureDay);
        setShowTimes(true);
      } else if (selectedInput === 'pickup'
        && calendarYear === pickupYear
        && calendarMonth + 1 === pickupMonth) {
        setCalendarDay(pickupDay);
        setShowTimes(true);
      }

      setCalendarMonth(calendarMonth + 1);
    }
  };

  const handleClickDate = (year, month, day) => {
    setCalendarDay(day);
    if (selectedInput === 'departure') {
      setDepartureYear(year);
      setDepartureMonth(month);
      setDepartureDay(day);
      setSelectedTime(null);
      setDepartureTime(null);
    } else if (selectedInput === 'pickup') {
      setPickupYear(year);
      setPickupMonth(month);
      setPickupDay(day);
      setSelectedTime(null);
      setPickupTime(null);
    }
    const time_data = {
      "success": true,
      "code": 200,
      "message": "Ok",
      "data": {
        "timeSlots": [
          {
              "time": 9,
              "available": true
          },
          {
              "time": 10,
              "available": true
          },
          {
              "time": 11,
              "available": true
          },
          {
              "time": 12,
              "available": false
          },
          {
              "time": 13,
              "available": false
          },
          {
              "time": 14,
              "available": false
          },
          {
              "time": 15,
              "available": true
          },
          {
              "time": 16,
              "available": true
          },
          {
              "time": 17,
              "available": true
          }
        ]
      }
    };
    setTimeData(time_data.data.timeSlots);
    setShowTimes(true);
  }

  const handleClickTime = (time) => {
    if (selectedTime === time) {
      setSelectedTime(null);
      if (selectedInput === 'departure')
        setDepartureTime(null);
      else
        setPickupTime(null);
    }
    else {
      setSelectedTime(time);
      if (selectedInput === 'departure')
        setDepartureTime(time);
      else
        setPickupTime(time);
    }
  }

  const handleClickDeparture = () => {
    setSelectedInput('departure');
    setCalendarYear(departureYear != null ? departureYear : currentYear);
    setCalendarMonth(departureMonth != null ? departureMonth : currentMonth);
    setCalendarDay(departureDay);
    setSelectedTime(departureTime);
  }

  const handleClickPickup = () => {
    setSelectedInput('pickup');
    setCalendarYear(pickupYear != null ? pickupYear : currentYear);
    setCalendarMonth(pickupMonth != null ? pickupMonth : currentMonth);
    setCalendarDay(pickupDay);
    setSelectedTime(pickupTime);
  }

  return (
    <>
      <div className={classNames('body')}>
        <div className={classNames('frame_left')}>
          <Calendar
            year={calendarYear}
            month={calendarMonth}
            day={calendarDay}
            handleClickDate={handleClickDate}
            handleLastMonth={handleLastMonth}
            handleNextMonth={handleNextMonth}
          />
          {showTimes && <TimeSlots timeSlots={timeData} selectedTime={selectedTime} handleClickTime={handleClickTime} />}
        </div>
        <div className={classNames('frame-right')}>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>1. 센터 방문</span>
            <span className={classNames('content', 'hover-pointer', selectedInput === 'departure' ? 'active' : null)} onClick={() => handleClickDeparture()}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{departureDay != null ? departureYear + '년 ' + (departureMonth + 1) + '월 ' + departureDay + '일' : null}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{departureTime != null ? departureTime + ' : 00' : null}</span>
              </div>
            </span>
          </div>
          <div className={classNames('category-row')}>
            <span className={classNames('title')}>2. 픽업 시간</span>
            <span className={classNames('content', 'hover-pointer', selectedInput === 'pickup' ? 'active' : null)} onClick={() => handleClickPickup()}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{pickupDay != null ? pickupYear + '년 ' + (pickupMonth + 1) + '월 ' + pickupDay + '일' : null}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{pickupTime != null ? pickupTime + ' : 00' : null}</span>
              </div>
            </span>
          </div>
        </div>
      </div>
      <div className={classNames('btn-group')}>
        <Link to="/" replace={true}>
          <BtnBlack text="이전" />
        </Link>
        <BtnBlack text="다음" onClick={handleNext} />
      </div>
    </>
  );
}
