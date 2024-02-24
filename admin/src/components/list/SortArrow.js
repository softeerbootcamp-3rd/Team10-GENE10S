import classNames from "classnames";

const SortArrow = ({ active, direction }) => {
  if (!active)
    return (
      <svg
        width='14'
        height='11'
        viewBox='0 0 10 10'
        fill='#999999'
        xmlns='http://www.w3.org/2000/svg'
      >
        <path d='M5 9L9 1H1L5 9Z' fill='#999999' />
      </svg>
    );

  if (direction === "asc")
    return (
      <svg
        width='14'
        height='11'
        viewBox='0 0 10 10'
        fill='#000000'
        xmlns='http://www.w3.org/2000/svg'
      >
        <path d='M5 1L1 9H9L5 1Z' fill='#000000' />
      </svg>
    );

  if (direction === "desc")
    return (
      <svg
        width='14'
        height='11'
        viewBox='0 0 10 10'
        fill='#000000'
        xmlns='http://www.w3.org/2000/svg'
      >
        <path d='M5 9L9 1H1L5 9Z' fill='#000000' />
      </svg>
    );
};

export default SortArrow;
