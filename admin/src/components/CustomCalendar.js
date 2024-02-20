import React, { useState, useEffect, useRef } from 'react';
import Calendar from 'react-calendar';
import '../styles/components/customCalendar.scss';
import moment from "moment";
import styled from "styled-components"
import selectArrow from '../assets/arrow.svg'


const CalendarContainer = styled.div`
  position: relative;
`;

const DropdownButton = styled.button`
  width: 200px;
  height: 48px;
  border: none;
  border-radius: 10px;
  padding: 0px 12px;
  color: lightslategrey;
  font-size: 16px;
  font-style: normal;
  font-weight: 500;
  line-height: 140%;
  text-align: start;
  appearance: none;
  background-color: white;
  background-image: url(${selectArrow});
  background-repeat: no-repeat;
  background-position: right 12px center;
  background-size: 12px;
`;

const CalendarWrapper = styled.div`
  z-index: 11;
  position: absolute;
  top: 100%;
  left: 0;
  display: ${(props) => (props.isOpen ? "block" : "none")};
`;

const CustomCalendar = ({ onChange, value }) => {
    const [nowDate, setNowDate] = useState("날짜");
    const [isOpen, setIsOpen] = useState(false);
  
    const calendarRef = useRef(null);

    const handleOutsideClick = (e) => {
      if (calendarRef.current && !calendarRef.current.contains(e.target)) {
        setIsOpen(false);
      }
    };

    useEffect(() => {
      document.addEventListener('click', handleOutsideClick);
      return () => {
        document.removeEventListener('click', handleOutsideClick);
      };
    }, []);

    const handleToggleCalendar = () => {
      setIsOpen(!isOpen);
    };
  
    const handleDateChange = (selectedDate) => {
      onChange(selectedDate);
      setIsOpen(false);
      setNowDate(moment(selectedDate).format("YYYY-MM-DD"));
    };
  
    return (
      <div ref={calendarRef}>
        <CalendarContainer>
          <DropdownButton onClick={handleToggleCalendar}>{nowDate}</DropdownButton>
          <CalendarWrapper isOpen={isOpen}>
            <Calendar
                onChange={handleDateChange}
                value={value}
                formatDay={(locale, date) => moment(date).format("D")}
                formatYear={(locale, date) => moment(date).format("YYYY")}
                formatMonthYear={(locale, date) => moment(date).format("YYYY. MM")}
                calendarType="gregory"
                showNeighboringMonth={false} 
                next2Label={null} 
                prev2Label={null}
                minDetail="year" 
            ></Calendar>
          </CalendarWrapper>
        </CalendarContainer>
      </div>
    );
  };

export default CustomCalendar;