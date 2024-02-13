import classNames from 'classnames';
import { BtnBlack } from './Button';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Calendar from './Calendar';
import TimeSlots from './TimeSlots';

export default function ModalDate({ nextStep, props }) {

  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;

  let departureYear = null;
  let departureMonth = null;
  let departureDay = null;
  let pickupYear = null;
  let pickupMonth = null;
  let pickupDay = null;

  const [departureDate, setDepartureDate] = useState(props.departureDate);
  const [departureTime, setDepartureTime] = useState(props.departureTime);
  const [pickupDate, setPickupDate] = useState(props.pickupDate);
  const [pickupTime, setPickupTime] = useState(props.pickupTime);

  const [showTimes, setShowTimes] = useState(false);
  const [timeData, setTimeData] = useState(null);

  const [selectedInput, setSelectedInput] = useState('departure');
  
  const [calendarYear, setCalendarYear] = useState(currentYear);
  const [calendarMonth, setCalendarMonth] = useState(currentMonth);
  const [calendarDay, setCalendarDay] = useState(null);

  const handleNext = () => {
    if (departureDate === '' || departureTime === '' || pickupDate === '' || pickupTime === '') return;
    nextStep(departureDate, departureTime, pickupDate, pickupTime);
  }

  const handleDate = (year, month, date) => {
    if (selectedInput == 'departure') {
      departureYear = year;
      departureMonth = month;
      departureDay = date;
      setDepartureDate(year + '년 ' + month + '월 ' + date + '일');
    } else if (selectedInput == 'pickup') {
      pickupYear = year;
      pickupMonth = month;
      pickupDay = date;
      setPickupDate(year + '년 ' + month + '월 ' + date + '일');
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

  const handleTime = (time) => {
    if (selectedInput == 'departure') {
      setDepartureTime(time + ' : 00');
    } else if (selectedInput == 'pickup') {
      setPickupTime(time + ' : 00');
    }
  }

  const handleUnselectDate = () => {
    setTimeData(null);
    setShowTimes(false);
  }

  const handleUnselectTime = () => {
    setDepartureTime('');
  }

  const handleClickDeparture = () => {
    setSelectedInput('departure');
    setCalendarYear(departureYear != null ? departureYear : currentYear);
    setCalendarMonth(departureMonth != null ? departureMonth : currentMonth);
    setCalendarDay(departureDay);
  }

  const handleClickPickup = () => {
    setSelectedInput('pickup');
    setCalendarYear(pickupYear != null ? pickupYear : currentYear);
    setCalendarMonth(pickupMonth != null ? pickupMonth : currentMonth);
    setCalendarDay(pickupDay);
  }

  return (
    <>
      <div className={classNames('body')}>
        <div className={classNames('frame_left')}>
          <Calendar year={calendarYear} month={calendarMonth} day={calendarDay} handleDate={handleDate} handleUnselect={handleUnselectDate}/>
          {showTimes && <TimeSlots timeSlots={timeData} handleTime={handleTime} handleUnselect={handleUnselectTime} />}
        </div>
        <div className={classNames('frame_right')}>
          <div className={classNames('category_row')}>
            <span className={classNames('title')}>1. 센터 방문</span>
            <span className={classNames('content', 'hover-pointer', selectedInput == 'departure' ? 'active' : null)} onClick={() => handleClickDeparture()}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{departureDate}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{departureTime}</span>
              </div>
            </span>
          </div>
          <div className={classNames('category_row')}>
            <span className={classNames('title')}>2. 픽업 시간</span>
            <span className={classNames('content', 'hover-pointer', selectedInput == 'pickup' ? 'active' : null)} onClick={() => handleClickPickup()}>
              <div className={classNames('input-area', 'w-200')}>
                <span className={classNames('input-text')}>{pickupDate}</span>
              </div>
              <div className={classNames('input-area', 'w-160')}>
                <span className={classNames('input-text')}>{pickupTime}</span>
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
