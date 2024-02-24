import className from "classnames";
import SortArrow from "./SortArrow";

const Table = ({ headerInfo, onSort, sortColumn, sortDirection, children }) => {
  return (
    <div className={className("table")}>
      <div className={className("th")}>
        {headerInfo.map((info, index) => {
          return (
            <div
              key={`th-${index}`}
              className={className(`td w-${info.width}`)}
              onClick={() => onSort(info.name)}
            >
              <span>{info.label}</span>
              {info.sortable && (
                <SortArrow
                  active={sortColumn === info.name}
                  direction={sortDirection}
                />
              )}
            </div>
          );
        })}
      </div>
      {children}
    </div>
  );
};

export default Table;
