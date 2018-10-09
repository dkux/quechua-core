import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPeriodo, defaultValue } from 'app/shared/model/periodo.model';

export const ACTION_TYPES = {
  FETCH_PERIODO_LIST: 'periodo/FETCH_PERIODO_LIST',
  FETCH_PERIODO: 'periodo/FETCH_PERIODO',
  CREATE_PERIODO: 'periodo/CREATE_PERIODO',
  UPDATE_PERIODO: 'periodo/UPDATE_PERIODO',
  DELETE_PERIODO: 'periodo/DELETE_PERIODO',
  RESET: 'periodo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPeriodo>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PeriodoState = Readonly<typeof initialState>;

// Reducer

export default (state: PeriodoState = initialState, action): PeriodoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PERIODO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PERIODO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PERIODO):
    case REQUEST(ACTION_TYPES.UPDATE_PERIODO):
    case REQUEST(ACTION_TYPES.DELETE_PERIODO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PERIODO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PERIODO):
    case FAILURE(ACTION_TYPES.CREATE_PERIODO):
    case FAILURE(ACTION_TYPES.UPDATE_PERIODO):
    case FAILURE(ACTION_TYPES.DELETE_PERIODO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERIODO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERIODO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PERIODO):
    case SUCCESS(ACTION_TYPES.UPDATE_PERIODO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PERIODO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/periodos';

// Actions

export const getEntities: ICrudGetAllAction<IPeriodo> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PERIODO_LIST,
  payload: axios.get<IPeriodo>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPeriodo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PERIODO,
    payload: axios.get<IPeriodo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPeriodo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PERIODO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPeriodo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PERIODO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPeriodo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PERIODO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
