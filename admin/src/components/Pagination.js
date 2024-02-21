import classNames from "classnames";
import { Link } from "react-router-dom";

export default function Pagination({ PageInfo, paginationSize }) {
  let pageNumber = PageInfo.page;
  let startPage =
    Math.floor((pageNumber - 1) / paginationSize) * paginationSize + 1;
  let endPage = startPage + paginationSize - 1;

  return (
    <>
      <div className={classNames("left-arrow")}>
        {startPage > 1 && (
          <Link to={`?page=${startPage - 1}`} state={{ page: startPage - 1 }}>
            &lt;
          </Link>
        )}
      </div>
      <div className={classNames("page-number")}>
        {Array.from(
          { length: Math.min(PageInfo.totalPages, endPage) - startPage + 1 },
          (_, index) => startPage + index
        ).map((pageNumber) => {
          const isActive = PageInfo.page === pageNumber;

          return (
            <Link
              to={`?page=${pageNumber}`}
              state={{ page: pageNumber }}
              className={classNames("page-item", { active: isActive })}
            >
              {pageNumber}
            </Link>
          );
        })}
      </div>
      <div className={classNames("right-arrow")}>
        {endPage < PageInfo.totalPages && (
          <Link to={`?page=${endPage + 1}`} state={{ page: endPage + 1 }}>
            &gt;
          </Link>
        )}
      </div>
    </>
  );
}
