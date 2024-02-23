import classNames from 'classnames'
import { Link } from 'react-router-dom'
import { useLocation } from 'react-router-dom'

export default function Pagination ({
  page,
  totalPages,
  paginationSize,
  onChange
}) {
  let pageNumber = page
  let startPage =
    Math.floor((pageNumber - 1) / paginationSize) * paginationSize + 1
  let endPage = startPage + paginationSize - 1

  const location = useLocation()
  const searchParams = new URLSearchParams(location.search)

  return (
    <>
      <div className={classNames('left-arrow')}>
        {startPage > 1 && (
          <Link
            to={`${location.pathname}?${searchParams.toString()}`}
            state={{ page: startPage - 1 }}
          >
            &lt;
          </Link>
        )}
      </div>
      <div className={classNames('page-number')}>
        {Array.from(
          { length: Math.min(totalPages, endPage) - startPage + 1 },
          (_, index) => startPage + index
        ).map((pageNumber, index) => {
          return (
            <Link
              to={`?page=${pageNumber}`}
              state={{ page: pageNumber }}
              className={classNames('page-item')}
              key={index}
              onClick={() => onChange(pageNumber)}
            >
              {pageNumber}
            </Link>
          )
        })}
      </div>
      <div className={classNames('right-arrow')}>
        {endPage < totalPages && (
          <Link to={`?page=${endPage + 1}`} state={{ page: endPage + 1 }}>
            &gt;
          </Link>
        )}
      </div>
    </>
  )
}
